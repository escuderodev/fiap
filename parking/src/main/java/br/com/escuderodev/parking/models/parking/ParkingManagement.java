package br.com.escuderodev.parking.models.parking;

import br.com.escuderodev.parking.models.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity(name = "ParkingManagement")
@Table(name = "parking_management")
@Getter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParkingManagement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentMethod;
    private BigDecimal fixedParkingPrice = new BigDecimal(10.00);
    private BigDecimal variableParkingPrice = new BigDecimal(15.00);
    private LocalDateTime startParking;
    private LocalDateTime stopParking;
    private Long usageTime;
    private Long fixedTime;
    private BigDecimal amountToPay;
    @ManyToOne
    private Vehicle vehicle;

    public ParkingManagement(Vehicle vehicle, ParkingRegistrationData data) {
        this.vehicle = vehicle;
        this.fixedTime = data.fixedTime();
        this.paymentMethod = data.paymentMethod();
        startParking();
    }

    public ParkingManagement() {

    }

    //    metodos auxiliares
    public void startParking() {
        TimeServer timeServer = new TimeServer();

        if (this.fixedTime != null && this.fixedTime > 0) {
            this.amountToPay = this.fixedParkingPrice.multiply(BigDecimal.valueOf(this.fixedTime));
            this.startParking = timeServer.getTimeServer();
            this.stopParking =  this.startParking.plusHours(this.fixedTime);
            this.usageTime= this.fixedTime;
        } else {
            this.startParking = timeServer.getTimeServer();
        }
    }

    public void stopParking() {
        TimeServer timeServer = new TimeServer();

        if (this.startParking != null) {
            this.stopParking = timeServer.getTimeServer(); //.plusHours(2);
            Duration parkedTime = Duration.between(startParking,stopParking);
            this.usageTime = parkedTime.toHours() + 1;
            this.amountToPay = this.variableParkingPrice.multiply(BigDecimal.valueOf(usageTime));

        } else {
            System.out.println("Erro: registro de entrada não encontrado!");

        }
    }

    public static void fixedParkingAlert(){
        System.out.println("Seu período de estacionamento está prestes a encerrar!");
    }

    public static void variableParkingAlert(){
        System.out.println("Seu período de estacionamento será renovado em 5 minutos caso não seja encerrado!");
    }
}
