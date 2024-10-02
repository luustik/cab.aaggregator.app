package cab.aggregator.app.driverservice.dto.request;

import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;


public record CarRequestDto (

        @NotNull(message = "{model.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{model.length}", groups = {OnCreate.class, OnUpdate.class})
        String model,

        @NotNull(message = "{carNumber.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{carNumber.length}", groups = {OnCreate.class, OnUpdate.class})
                        @Pattern(regexp = "^\\d{4}[A-Za-z]{2}-[1-7]$")
        String carNumber,

        @NotNull(message = "{carNumber.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Length(max = 255, message = "{carNumber.length}", groups = {OnCreate.class, OnUpdate.class})
        String color,

        @NotNull(message = "{driverId.notnull}", groups = {OnCreate.class, OnUpdate.class})
                @Positive
        Integer driverId
) {

}
