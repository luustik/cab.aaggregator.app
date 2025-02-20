package cab.aggregator.app.driverservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static cab.aggregator.app.driverservice.utility.Constants.ACTUATOR_ENDPOINT;
import static cab.aggregator.app.driverservice.utility.Constants.API_DOCS_ENDPOINT;
import static cab.aggregator.app.driverservice.utility.Constants.SWAGGER_RESOURCES;
import static cab.aggregator.app.driverservice.utility.Constants.SWAGGER_UI_ENDPOINT;
import static cab.aggregator.app.driverservice.utility.Constants.WEBJARS_ENDPOINT;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(c -> c
                        .requestMatchers(
                                ACTUATOR_ENDPOINT,
                                SWAGGER_UI_ENDPOINT,
                                API_DOCS_ENDPOINT,
                                SWAGGER_RESOURCES,
                                WEBJARS_ENDPOINT
                        ).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(configurer ->
                        configurer
                                .jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtTokenConverter());
        return converter;
    }
}
