package com.crazymeal.montpelliermobility.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parking {
	
	@Id
	private String id;

	private String name;
	private String technicalName;
	private DateTime lastUpdated;
	private String status;
	private int freePlaces;
	private int totalPlaces;
}
