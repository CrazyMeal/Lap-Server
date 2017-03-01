package fr.lap.db;

import org.springframework.data.repository.CrudRepository;

import fr.lap.domain.BasicParkingData;

public interface BasicParkingDataRepository extends CrudRepository<BasicParkingData, Long>{

}
