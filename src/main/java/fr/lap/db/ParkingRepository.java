package fr.lap.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.lap.domain.Parking;

public interface ParkingRepository extends CrudRepository<Parking, Long> {
	List<Parking> findByName(String name);
}
