package br.com.escuderodev.parking.models.vehicle;

import jakarta.validation.constraints.NotBlank;

public record VehicleRegistrationData(
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @NotBlank
        String plate
) {
}
