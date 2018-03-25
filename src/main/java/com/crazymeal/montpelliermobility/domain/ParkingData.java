package com.crazymeal.montpelliermobility.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "parking_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingData {

	@Id
	private String id;

	@DBRef
	private Parking parking;

	private String name;
	private String status;
	private int freePlaces;
	private int totalPlaces;
	private Date dateOfData;
}
