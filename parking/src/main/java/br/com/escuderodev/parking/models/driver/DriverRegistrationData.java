package br.com.escuderodev.parking.models.driver;

import jakarta.validation.constraints.NotBlank;

public record DriverRegistrationData(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        String address
) {
}
