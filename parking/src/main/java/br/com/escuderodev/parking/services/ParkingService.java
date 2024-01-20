package br.com.escuderodev.parking.services;

import br.com.escuderodev.parking.models.notification.email.EmailDetails;
import br.com.escuderodev.parking.models.notification.sms.SMSDetails;
import br.com.escuderodev.parking.models.parking.*;
import br.com.escuderodev.parking.models.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ParkingService {
    private ParkingRepository parkingRepository;
    private VehicleRepository vehicleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    public ParkingService(ParkingRepository parkingRepository, VehicleRepository vehicleRepository) {
        this.parkingRepository = parkingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Page<ParkingListData> findAll(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        return parkingRepository.findAll(pagination).map(ParkingListData::new);
    }

    public Optional<ParkingDetails> findById(Long id) {
        return parkingRepository.findById(id);
    }

    public ParkingDetails create(ParkingRegistrationData data, Long id) {

        var typedVehicle = vehicleRepository.getReferenceById(id);
        var parking = new ParkingDetails(typedVehicle, data);
        var parkingSaved = parkingRepository.save(parking);

        var sms = new SMSDetails();

        var email = new EmailDetails();

        if (parkingSaved.getFixedTime() != null && parkingSaved.getFixedTime() > 0) {
            sms.setTo(typedVehicle.getDriver().getPhone());
            sms.setMessage(parkingSaved.initParkingAlert());
            smsService.sendSMS(sms.getTo(), sms.getMessage());

            email.setRecipient("escuderodev@gmail.com");
            email.setSubject("Registro de Parking Fixo");
            email.setMessageBody(parkingSaved.fixedParkingAlert());
            emailService.sendMail(email);
        } else {
            sms.setTo(typedVehicle.getDriver().getPhone());
            sms.setMessage(parkingSaved.initParkingAlert());
            smsService.sendSMS(sms.getTo(), sms.getMessage());

            email.setRecipient("escuderodev@gmail.com");
            email.setSubject("Registro de Parking Variável");
            email.setMessageBody(parking.variableParkingAlert());
            emailService.sendMail(email);
        }
        return parkingSaved;
    }

    public ParkingDetails update(Long id) {
        var typedParking = parkingRepository.getReferenceById(id);
        typedParking.stopParking();
        return typedParking;
    }

    public void delete(Long id) {
        parkingRepository.deleteById(id);
    }
}
