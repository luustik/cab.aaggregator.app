package cab.aggregator.app.rideservice.client;

import cab.aggregator.app.rideservice.dto.client.ClientErrorResponse;
import cab.aggregator.app.rideservice.exception.ExternalClientException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static cab.aggregator.app.rideservice.utility.Constants.MESSAGE;

@Component
public class ExternalClientErrorDecoder implements ErrorDecoder {

    @Override
    @SneakyThrows
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(readResponseBody(response));
        String message = jsonNode.get(MESSAGE).asText();
        ClientErrorResponse clientErrorResponse = ClientErrorResponse.builder()
                .status(HttpStatus.valueOf(response.status()))
                .message(message)
                .build();
        return new ExternalClientException(clientErrorResponse);
    }

    @SneakyThrows
    protected String readResponseBody(Response response) {
            @Cleanup
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8));
            return bufferedReader.lines()
                    .collect(Collectors.joining("\n"));
    }
}