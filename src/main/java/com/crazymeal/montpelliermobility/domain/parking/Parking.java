package com.crazymeal.montpelliermobility.domain.parking;

import com.crazymeal.montpelliermobility.domain.city.City;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Parking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastGrabTime;
	
	@ManyToOne(optional = false, targetEntity = City.class)
	private City city;
	
	public Parking(String name, City city, Date lastGrabTime) {
		super();
		this.name = name;
		this.lastGrabTime = lastGrabTime;
		this.city = city;
	}
}
