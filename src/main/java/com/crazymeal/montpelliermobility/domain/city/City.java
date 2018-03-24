package com.crazymeal.montpelliermobility.domain.city;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class City {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NonNull @Column(nullable = false)
	private String name;
	
	public City(String name) {
		super();
		this.name = name;
	}
}
