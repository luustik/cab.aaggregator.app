package cab.aggregator.app.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AdminResponseTokenDto(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("token_type")
        String tokenType
) {
}
