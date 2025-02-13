package cab.aggregator.app.authservice.service.impl;

import cab.aggregator.app.authservice.client.driver.DriverClientContainer;
import cab.aggregator.app.authservice.client.passenger.PassengerClientContainer;
import cab.aggregator.app.authservice.config.KeycloakProperties;
import cab.aggregator.app.authservice.dto.request.SignInDto;
import cab.aggregator.app.authservice.dto.request.SignUpDto;
import cab.aggregator.app.authservice.dto.response.UserResponseTokenDto;
import cab.aggregator.app.authservice.exception.CreateUserException;
import cab.aggregator.app.authservice.exception.KeycloakException;
import cab.aggregator.app.authservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.Response;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static cab.aggregator.app.authservice.util.Constants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;
    private final KeycloakProperties keycloakProperties;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;
    private final PassengerClientContainer passengerClientContainer;
    private final DriverClientContainer driverClientContainer;

    @Override
    public void signUp(SignUpDto signUpDto) {
        UserRepresentation keycloakUser = getUserRepresentation(signUpDto);
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        UsersResource usersResource = realmResource.users();
        String adminClientAccessToken = keycloak.tokenManager().getAccessTokenString();
        Response response;
        response = Optional.ofNullable(usersResource.create(keycloakUser))
                .orElseThrow(() -> new ServiceUnavailableException(messageSource.getMessage(SERVICE_UNAVAILABLE_MESSAGE, new Object[]{}, LocaleContextHolder.getLocale())));
        if (response.getStatus() == HttpStatus.CREATED.value()) {
            try {
                switch (signUpDto.role()) {
                    case PASSENGER_ROLE ->
                            passengerClientContainer.createPassenger(signUpDto, AUTH_TOKEN + adminClientAccessToken);
                    case DRIVER_ROLE ->
                            driverClientContainer.createDriver(signUpDto, AUTH_TOKEN + adminClientAccessToken);
                }
            } catch (Exception exception) {
                usersResource.delete(CreatedResponseUtil.getCreatedId(response));
                throw exception;
            }
        } else {
            throw new CreateUserException(messageSource.getMessage(readResponseBody(response), new Object[]{}, LocaleContextHolder.getLocale()),
                    response.getStatus());
        }
        RolesResource rolesResource = realmResource.roles();
        RoleRepresentation role = rolesResource.get(signUpDto.role()).toRepresentation();
        UserResource userById = usersResource.get(CreatedResponseUtil.getCreatedId(response));
        userById.roles()
                .realmLevel()
                .add(List.of(role));
    }

    @Override
    @SneakyThrows
    public UserResponseTokenDto signIn(SignInDto signInDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(GRANT_TYPE_FIELD, GRANT_TYPE_PASSWORD_FIELD);
        body.add(USERNAME_FIELD, signInDto.email());
        body.add(PASSWORD_FIELD, signInDto.password());
        body.add(CLIENT_ID_FIELD, keycloakProperties.getAuth().getClientId());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = getResponseWithToken(requestEntity);

        handleAnyStatusCodeExceptOk(response);

        return objectMapper.readValue(response.getBody(),
                UserResponseTokenDto.class);
    }

    private void handleAnyStatusCodeExceptOk(ResponseEntity<String> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new KeycloakException(messageSource.getMessage(Objects.requireNonNull(response.getBody()), new Object[]{}, LocaleContextHolder.getLocale()),
                    response.getStatusCode().value());
        }
    }

    private String getAuthUrl() {
        return keycloakProperties.getUserManagement().getServerUrl() +
                "/realms/" + keycloakProperties.getRealm() +
                "/protocol/openid-connect/token";
    }

    private ResponseEntity<String> getResponseWithToken(HttpEntity<MultiValueMap<String, String>> requestEntity) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(getAuthUrl(), HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException exception) {
            throw new CreateUserException(messageSource.getMessage(exception.getMessage(), new Object[]{}, LocaleContextHolder.getLocale()),
                    exception.getStatusCode().value());
        }
        return response;
    }

    private UserRepresentation getUserRepresentation(SignUpDto signUpDto) {
        UserRepresentation keycloakUser = new UserRepresentation();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(signUpDto.password());

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put(GENDER_FIELD, List.of(signUpDto.gender()));

        keycloakUser.setFirstName(signUpDto.name());
        keycloakUser.setEmail(signUpDto.email());
        keycloakUser.setCredentials(List.of(credential));
        keycloakUser.setEnabled(true);
        keycloakUser.setAttributes(attributes);
        return keycloakUser;
    }

    @SneakyThrows
    private String readResponseBody(Response response) {
        if (Objects.nonNull(response)) {
            @Cleanup InputStreamReader inputStreamReader = new InputStreamReader(response.readEntity(InputStream.class),
                    StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString()
                    .replace("\"errorMessage\":", "")
                    .replace("\"", "");
        }
        return "";
    }
}
