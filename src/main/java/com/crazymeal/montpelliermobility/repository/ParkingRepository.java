package com.crazymeal.montpelliermobility.repository;

import com.crazymeal.montpelliermobility.domain.Parking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ParkingRepository extends ReactiveMongoRepository<Parking, String> {

	Mono<Parking> findByTechnicalName(String technicalName);
}
