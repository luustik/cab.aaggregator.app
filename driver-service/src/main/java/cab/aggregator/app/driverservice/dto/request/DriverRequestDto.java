package cab.aggregator.app.driverservice.dto.request;

import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DriverRequest DTO")
public record DriverRequestDto (
@Schema(description = "Driver name", example = "Pasha")
        @NotNull(message = "{name.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{name.length}", groups = {OnCreate.class, OnUpdate.class})
        String name,
@Schema(description = "Driver email", example = "nvienjnb@knsb.com")
        @NotNull(message = "{email.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{email.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w.-]+$")
        String email,
@Schema(description = "Driver phone number", example = "+375(29)1234567")
        @NotNull(message = "{phoneNumber.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{phoneNumber.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "\\+375\\((29|44|33|25)\\)\\d{7}$", message = "{phoneNumber.pattern}")
        String phoneNumber,
@Schema(description = "Driver gender", example = "FEMALE/MALE")
        @NotNull(message = "{gender.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{gender.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "(MALE|FEMALE)")
        String gender
){
}
