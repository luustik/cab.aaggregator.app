package cab.aggregator.app.driverservice.dto.request;

import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public record DriverRequestDto (

        @NotNull(message = "{name.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{name.length}", groups = {OnCreate.class, OnUpdate.class})
        String name,

        @NotNull(message = "{email.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{email.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w.-]+$")
        String email,

        @NotNull(message = "{phoneNumber.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{phoneNumber.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "\\+375\\((29|44|33|25)\\)\\d{7}$")
        String phoneNumber,

        @NotNull(message = "{gender.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{gender.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "(MALE|FEMALE)")
        String gender
){
}
