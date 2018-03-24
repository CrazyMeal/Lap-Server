package com.crazymeal.montpelliermobility.db;

import com.crazymeal.montpelliermobility.domain.parking.Parking;
import org.springframework.data.repository.CrudRepository;

public interface ParkingRepository extends CrudRepository<Parking, Long> {
	Parking findByName(String name);
}
