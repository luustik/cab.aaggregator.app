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

        @NotNull(message = "{email.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{email.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "\\+375\\((29|44|33|25)\\)\\d{7}$")
        String phoneNumber,

        @NotNull(message = "{message.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{message.length}", groups = {OnCreate.class, OnUpdate.class})
        String gender,

        @NotNull(message = "{model.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{model.length}", groups = {OnCreate.class, OnUpdate.class})
        Set<Integer> carsId
){

}
