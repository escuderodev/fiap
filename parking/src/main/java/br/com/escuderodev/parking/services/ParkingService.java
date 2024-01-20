package br.com.escuderodev.parking.services;

import br.com.escuderodev.parking.models.email.EmailDetails;
import br.com.escuderodev.parking.models.parking.*;
import br.com.escuderodev.parking.models.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ParkingService {
    private ParkingRepository parkingRepository;
    private VehicleRepository vehicleRepository;

    @Autowired
    private EmailService emailService;

    public ParkingService(ParkingRepository parkingRepository, VehicleRepository vehicleRepository) {
        this.parkingRepository = parkingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Page<ParkingListData> findAll(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        return parkingRepository.findAll(pagination).map(ParkingListData::new);
    }

    public Optional<ParkingManagement> findById(Long id) {
        return parkingRepository.findById(id);
    }

    public ParkingManagement create(ParkingRegistrationData data, Long id) {
        TimeServer timeServer = new TimeServer();

        var typedVehicle = vehicleRepository.getReferenceById(id);
        var parking = new ParkingManagement(typedVehicle, data);
        var parkingSaved = parkingRepository.save(parking);

        var email = new EmailDetails();

        if (parkingSaved.getFixedTime() != null && parkingSaved.getFixedTime() > 0) {
            email.setRecipient("escuderodev@gmail.com");
            email.setSubject("Registro de Parking Fixo");
            email.setMessageBody(String.format("""
                                                === Você iniciou um Estacionamento com Preço Fixo ===
                                                
                                                Id: %d
                                                Veículo: %s
                                                Modelo: %s
                                                Placa: %s
                                                Condutor: %s
                                                Valor por hora R$: %.2f
                                                Tempo utilizado em horas: %d
                                                Valor a pagar R$: %.2f
                                                
                                                """, parkingSaved.getId(), parkingSaved.getVehicle().getBrand(), parkingSaved.getVehicle().getModel(), parkingSaved.getVehicle().getPlate(),
                                                    parkingSaved.getVehicle().getDriver().getName(), parkingSaved.getFixedParkingPrice(),
                                                    parkingSaved.getUsageTime(), parkingSaved.getAmountToPay()));
            emailService.sendMail(email);
        } else {
            email.setRecipient("escuderodev@gmail.com");
            email.setSubject("Registro de Parking Variável");
            email.setMessageBody(String.format("""
                                                === Você iniciou um Estacionamento com Preço Variável ===
                                                
                                                Id: %d
                                                Veículo: %s
                                                Modelo: %s
                                                Placa: %s
                                                Condutor: %s
                                                Valor por hora R$: %.2f
                                                
                                                Obs.: O valor a pagar será informado quando o parking for encerrado!
                                                """, parkingSaved.getId(), parkingSaved.getVehicle().getBrand(), parkingSaved.getVehicle().getModel(), parkingSaved.getVehicle().getPlate(),
                    parkingSaved.getVehicle().getDriver().getName(), parkingSaved.getVariableParkingPrice()));
            emailService.sendMail(email);
        }
        return parkingSaved;
    }

    public ParkingManagement update(Long id) {
        var typedParking = parkingRepository.getReferenceById(id);
        typedParking.stopParking();
        return typedParking;
    }

    public void delete(Long id) {
        parkingRepository.deleteById(id);
    }
}
