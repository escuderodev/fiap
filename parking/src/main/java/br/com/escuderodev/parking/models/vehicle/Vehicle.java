package br.com.escuderodev.parking.models.vehicle;

import br.com.escuderodev.parking.models.driver.Driver;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Vehicle")
@Table(name = "vehicles")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String plate;
    @ManyToOne
    private Driver driver;

    public Vehicle(VehicleRegistrationData data, Driver driver) {
        this.brand = data.brand();
        this.model = data.model();
        this.plate = data.plate();
        this.driver = driver;
    }

    public Vehicle() {
    }

    public void updateData(Vehicle vehicle) {
        this.brand = vehicle.brand;
        this.model = vehicle.model;
        this.plate = vehicle.plate;
    }
}
