package cab.aggregator.app.rideservice.dto.request;

import cab.aggregator.app.rideservice.dto.validation.OnCreate;
import cab.aggregator.app.rideservice.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Ride Request DTO")
public record RideRequest(
        @Schema(description = "Driver id")
        @NotNull(message = "{driverId.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Positive
        Long driverId,

        @Schema(description = "Passenger id")
        @NotNull(message = "{passengerId.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Positive
        Long passengerId,

        @Schema(description = "Departure address", example = "Independency 1")
        @NotNull(message = "{departureAddress.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 255, message = "{departureAddress.length}", groups = {OnCreate.class, OnUpdate.class})
        String departureAddress,

        @Schema(description = "Arrival address", example = "Independency 1")
        @NotNull(message = "{arrivalAddress.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 255, message = "{arrivalAddress.length}", groups = {OnCreate.class, OnUpdate.class})
        String arrivalAddress,

        @Schema(description = "Status ride", example = "COMPLETED")
        @NotNull(message = "{status.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 255, message = "{status.length}", groups = {OnCreate.class, OnUpdate.class})
        @Pattern(regexp = "^(CREATED|ACCEPTED|WAY_TO_PASSENGER|WAY_TO_DESTINATION|COMPLETED|CANCELLED)$",
                message = "{status.pattern}")
        String status,

        @Schema(description = "Order date and time", example = "2024-10-21T19:30:00")
        @NotNull(message = "{date.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 255, message = "{date.length}", groups = {OnCreate.class, OnUpdate.class})
        @Pattern(regexp = "^(20[0-9]{2}|21[0-0])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-9][0-9])$",
                message = "{date.pattern}")
        String orderDateTime,

        @Schema(description = "Ride cost", example = "35")
        @NotNull(message = "{cost.notnull}", groups = {OnCreate.class, OnUpdate.class})
        @Min(0)
        @Max(999)
        BigDecimal cost
) {
}
