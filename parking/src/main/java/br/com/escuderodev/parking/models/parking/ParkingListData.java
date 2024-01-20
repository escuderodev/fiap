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
    public ParkingListData(ParkingManagement parkingManagement) {
        this(
                parkingManagement.getId(),
                parkingManagement.getPaymentMethod(),
                parkingManagement.getFixedParkingPrice(),
                parkingManagement.getVariableParkingPrice(),
                parkingManagement.getStartParking(),
                parkingManagement.getStopParking(),
                parkingManagement.getUsageTime(),
                parkingManagement.getAmountToPay(),
                parkingManagement.getVehicle()
                );
    }
}
