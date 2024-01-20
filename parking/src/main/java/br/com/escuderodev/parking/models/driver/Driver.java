package br.com.escuderodev.parking.models.driver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Driver")
@Table(name = "drivers")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Driver {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String address;

    public Driver(DriverRegistrationData data) {
        this.name = data.name();
        this.phone = data.phone();
        this.address = data.address();
    }

    public Driver() {
    }

    public void updateData(Driver driver) {
        this.name = driver.name;
        this.phone = driver.phone;
        this.address = driver.address;
    }
}
