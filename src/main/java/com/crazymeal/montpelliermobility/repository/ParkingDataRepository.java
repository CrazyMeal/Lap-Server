package com.crazymeal.montpelliermobility.repository;

import com.crazymeal.montpelliermobility.domain.ParkingData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ParkingDataRepository extends ReactiveMongoRepository<ParkingData, String> {
}
