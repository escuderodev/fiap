package br.com.escuderodev.parking.models.parking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParkingRegistrationData(
        @NotNull
        Long fixedTime,
        @NotBlank
        String paymentMethod

) {
}
