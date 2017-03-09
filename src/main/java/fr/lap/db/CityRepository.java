package fr.lap.db;

import org.springframework.data.repository.CrudRepository;

import fr.lap.domain.city.City;

public interface CityRepository extends CrudRepository<City, Long> {
	City findByName(String name);
}
