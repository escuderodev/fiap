package br.com.escuderodev.parking.services;

import br.com.escuderodev.parking.models.driver.Driver;
import br.com.escuderodev.parking.models.driver.DriverListData;
import br.com.escuderodev.parking.models.driver.DriverRegistrationData;
import br.com.escuderodev.parking.models.driver.DriverRepository;
import br.com.escuderodev.parking.models.vehicle.Vehicle;
import br.com.escuderodev.parking.models.vehicle.VehicleListData;
import br.com.escuderodev.parking.models.vehicle.VehicleRegistrationData;
import br.com.escuderodev.parking.models.vehicle.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {
    private VehicleRepository repository;
    private DriverRepository driverRepository;

    public VehicleService(VehicleRepository repository, DriverRepository driverRepository) {
        this.repository = repository;
        this.driverRepository = driverRepository;
    }

    public Page<VehicleListData> findAll(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        return repository.findAll(pagination).map(VehicleListData::new);
    }

    public Optional<Vehicle> findById(Long id) {
        return repository.findById(id);
    }

    public Vehicle create(VehicleRegistrationData data, Long id) {
        var typedDriver = driverRepository.getReferenceById(id);
        var vehicle = new Vehicle(data,typedDriver);
        return repository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle) {
        var typedVehicle = repository.getReferenceById(vehicle.getId());
        typedVehicle.updateData(vehicle);
        return typedVehicle;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
