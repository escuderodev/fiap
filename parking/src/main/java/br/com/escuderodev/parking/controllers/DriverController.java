package br.com.escuderodev.parking.controllers;

import br.com.escuderodev.parking.models.driver.Driver;
import br.com.escuderodev.parking.models.driver.DriverListData;
import br.com.escuderodev.parking.models.driver.DriverRegistrationData;
import br.com.escuderodev.parking.services.DriverService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/drivers")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService service;

    @GetMapping
    public ResponseEntity<Page<DriverListData>> findAllDriver(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        var page = service.findAll(pagination);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Transactional
    public ResponseEntity registerDriver(@RequestBody @Valid DriverRegistrationData data, UriComponentsBuilder uriBuilder) {
        var driver = service.create(data);
        var uri = uriBuilder.path("drivers/{id}").buildAndExpand(driver.getId()).toUri();
        return ResponseEntity.created(uri).body(driver);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateDriver(@RequestBody @Valid Driver driver) {
        var typedDriver = driver;
        service.update(typedDriver);
        return ResponseEntity.ok(typedDriver);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDriver(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
