package br.com.escuderodev.parking.controllers;

import br.com.escuderodev.parking.models.parking.ParkingListData;
import br.com.escuderodev.parking.models.parking.ParkingDetails;
import br.com.escuderodev.parking.models.parking.ParkingRegistrationData;
import br.com.escuderodev.parking.services.ParkingService;
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
@RequestMapping("/parking")
@CrossOrigin
public class ParkingController {

    @Autowired
    private ParkingService service;

    @GetMapping
    public ResponseEntity<Page<ParkingListData>> findAllParking(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pagination) {
        var page = service.findAll(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ParkingDetails>> findParkingById(@PathVariable Long id) {
        var parking = service.findById(id);
        return ResponseEntity.ok(parking);
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity startParking(@PathVariable Long id, @RequestBody @Valid ParkingRegistrationData data, UriComponentsBuilder uriBuilder) {
        var parking = service.create(data, id);
        var uri = uriBuilder.path("parking/{id}").buildAndExpand(parking.getId()).toUri();
        return ResponseEntity.created(uri).body(parking);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity stopParking(@PathVariable Long id) {
        var updatedParking = service.update(id);
        return ResponseEntity.ok(updatedParking);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteParking(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
