package br.com.escuderodev.parking.models.parking;

import br.com.escuderodev.parking.models.vehicle.Vehicle;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ParkingListData(
        Long id,
        String paymentMethod,
        BigDecimal fixedParkingPrice,
        BigDecimal variableParkingPrice,
        LocalDateTime startParking,
        LocalDateTime stopParking,
        Long usageTime,
        BigDecimal amountToPay,
        Vehicle vehicle
) {
    public ParkingListData(ParkingDetails parkingDetails) {
        this(
                parkingDetails.getId(),
                parkingDetails.getPaymentMethod(),
                parkingDetails.getFixedParkingPrice(),
                parkingDetails.getVariableParkingPrice(),
                parkingDetails.getStartParking(),
                parkingDetails.getStopParking(),
                parkingDetails.getUsageTime(),
                parkingDetails.getAmountToPay(),
                parkingDetails.getVehicle()
                );
    }
}
