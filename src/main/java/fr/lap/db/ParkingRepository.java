package fr.lap.db;

import org.springframework.data.repository.CrudRepository;

import fr.lap.domain.parking.Parking;

public interface ParkingRepository extends CrudRepository<Parking, Long> {
	Parking findByName(String name);
}
