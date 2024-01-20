package br.com.escuderodev.parking.services;

import br.com.escuderodev.parking.models.driver.Driver;
import br.com.escuderodev.parking.models.driver.DriverListData;
import br.com.escuderodev.parking.models.driver.DriverRegistrationData;
import br.com.escuderodev.parking.models.driver.DriverRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {
    private DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Page<DriverListData> findAll(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        return repository.findAll(pagination).map(DriverListData::new);
    }

    public Optional<Driver> findById(Long id) {
        return repository.findById(id);
    }

    public Driver create(DriverRegistrationData data) {
        var driver = new Driver(data);
        return repository.save(driver);
    }

    public Driver update(Driver driver) {
        var typedDriver = repository.getReferenceById(driver.getId());
        typedDriver.updateData(driver);
        return typedDriver;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
