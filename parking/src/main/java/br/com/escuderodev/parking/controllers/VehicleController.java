package br.com.escuderodev.parking.controllers;

import br.com.escuderodev.parking.models.vehicle.Vehicle;
import br.com.escuderodev.parking.models.vehicle.VehicleListData;
import br.com.escuderodev.parking.models.vehicle.VehicleRegistrationData;
import br.com.escuderodev.parking.services.VehicleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin
public class VehicleController {

    @Autowired
    private VehicleService service;
    @GetMapping
    public ResponseEntity<Page<VehicleListData>> findAllVehicle(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        var page = service.findAll(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Vehicle>> findVehicleById(@PathVariable Long id) {
        var vehicle = service.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity registerVehicle(@PathVariable Long id, @RequestBody @Valid VehicleRegistrationData data, UriComponentsBuilder uriBuilder) {
        var vehicle = service.create(data, id);
        var uri = uriBuilder.path("drivers/{id}").buildAndExpand(vehicle.getId()).toUri();
        return ResponseEntity.created(uri).body(vehicle);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateVehicle(@RequestBody @Valid Vehicle vehicle) {
        var typedVehicle = vehicle;
        service.update(typedVehicle);
        var updatedVehicle = service.findById(typedVehicle.getId());
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteVehicle(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
