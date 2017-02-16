package lap.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lap.model.Parking;

public interface ParkingRepository extends CrudRepository<Parking, Long> {
	List<Parking> findByName(String name);
}
