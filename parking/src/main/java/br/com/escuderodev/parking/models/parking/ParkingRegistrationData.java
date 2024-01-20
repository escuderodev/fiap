package br.com.escuderodev.parking.models.parking;

import jakarta.validation.constraints.NotBlank;
public record ParkingRegistrationData(
        Long fixedTime,
        @NotBlank
        String paymentMethod

) {
}
