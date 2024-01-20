package br.com.escuderodev.parking.models.vehicle;

import br.com.escuderodev.parking.models.driver.Driver;
import jakarta.validation.constraints.NotBlank;

public record VehicleListData(
        Long id,
        String brand,
        String model,
        String plate,
        Driver driver
) {
    public VehicleListData(Vehicle vehicle) {
        this(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getPlate(), vehicle.getDriver());
    }
}
