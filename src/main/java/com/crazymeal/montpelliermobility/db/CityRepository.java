package com.crazymeal.montpelliermobility.db;

import org.springframework.data.repository.CrudRepository;

import com.crazymeal.montpelliermobility.domain.city.City;

public interface CityRepository extends CrudRepository<City, Long> {
	City findByName(String name);
}
