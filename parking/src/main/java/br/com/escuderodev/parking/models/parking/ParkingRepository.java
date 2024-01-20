package br.com.escuderodev.parking.models.parking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<ParkingManagement, Long> {
}
