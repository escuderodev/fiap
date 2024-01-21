package br.com.escuderodev.parking.models.parking;

import br.com.escuderodev.parking.models.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity(name = "ParkingDetails")
@Table(name = "parking_details")
@Getter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParkingDetails {
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

    public ParkingDetails(Vehicle vehicle, ParkingRegistrationData data) {
        this.vehicle = vehicle;
        this.fixedTime = data.fixedTime();
        this.paymentMethod = data.paymentMethod();
        startParking();
    }

    public ParkingDetails() {

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
            this.usageTime = 1l;
        }
    }

    public void stopParking() {
        TimeServer timeServer = new TimeServer();

        if (this.startParking != null) {
            this.stopParking = timeServer.getTimeServer();
            Duration parkedTime = Duration.between(startParking,stopParking);
            this.usageTime = parkedTime.toHours() + 1;
            this.amountToPay = this.variableParkingPrice.multiply(BigDecimal.valueOf(usageTime));

        } else {
            System.out.println("Erro: registro de entrada não encontrado!");

        }
    }

    public String fixedParkingAlert() {
        return String.format("""
                    === Voce iniciou um estacionamento fixo para o veículo abaixo ===
                    
                    Veículo
                    Marca: %s
                    Modelo: %s
                    Placa: %s
                    Tempo solicitado em horas: %s
                    Valor por hora R$: %.2f
                    Total à pagar R$: %.2f
                    """, this.vehicle.getBrand(), this.vehicle.getModel(), this.vehicle.getPlate(), this.fixedTime, this.fixedParkingPrice, this.amountToPay);

    }

    public String variableParkingAlert(){
        return String.format("""
                    === Voce iniciou um estacionamento variável para o veiculo abaixo ===
                    
                    Veiculo
                    Marca: %s
                    Modelo: %s
                    Placa: %s
                    Valor por hora R$: %.2f
                    
                    Obs.: o valor a pagar será informado no encerramento do serviço.
                    """, this.vehicle.getBrand(), this.vehicle.getModel(), this.vehicle.getPlate(), this.variableParkingPrice);
    }

    public String initParkingAlert() {
        return String.format("""
                    
                    === Voce iniciou um estacionamento para o veiculo abaixo ===
                    
                    == Veiculo ==
                    Marca: %s
                    Modelo: %s
                    Placa: %s
                    """, this.vehicle.getBrand(), this.vehicle.getModel(), this.vehicle.getPlate());
    }

    public String stopParkingAlert() {
        return String.format("""
                    
                   Atencao: Seu tempo está acabando. Caso este estacionamento nao seja encerrado em 5 minutos, ele será renovado em mais 1 hora.
                    
                    == Veiculo ==
                    Marca: %s
                    Modelo: %s
                    Placa: %s
                    """, this.vehicle.getBrand(), this.vehicle.getModel(), this.vehicle.getPlate());
    }
}