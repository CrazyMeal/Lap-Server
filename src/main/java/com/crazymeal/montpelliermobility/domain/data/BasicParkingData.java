package com.crazymeal.montpelliermobility.domain.data;

import com.crazymeal.montpelliermobility.domain.parking.Parking;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class BasicParkingData extends ParkingData implements Serializable {
	
	private static final long serialVersionUID = -5863342557061517768L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int freePlaces;
	
	@Column(nullable = false)
	private int totalPlaces;
	
	@Column(nullable = true)
	private String status;
	
	@Column(nullable = true, columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfData;
	
	@ManyToOne(optional = false , targetEntity = Parking.class)
	private Parking parking;
	
	public BasicParkingData(int freePlaces, int totalPlaces, String status, Date dateOfData, Parking parking) {
		super();
		this.freePlaces = freePlaces;
		this.totalPlaces = totalPlaces;
		this.status = status;
		this.dateOfData = dateOfData;
		this.parking = parking;
	}
}
