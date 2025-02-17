package cab.aggregator.app.authservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInAdminDto(

        @NotBlank
        String clientId,

        @NotBlank
        String clientSecret
) {
}
