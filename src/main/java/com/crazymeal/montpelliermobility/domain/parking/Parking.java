package com.crazymeal.montpelliermobility.domain.parking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.crazymeal.montpelliermobility.domain.city.City;

@Entity
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
	
	public Parking() {
		super();
	}
	
	public Parking(String name, City city, Date lastGrabTime) {
		super();
		this.name = name;
		this.lastGrabTime = lastGrabTime;
		this.city = city;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parking other = (Parking) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Parking [name=" + name + ", lastGrabTime=" + lastGrabTime + ", city=" + city + "]";
	}

	public String getName() {
		return name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getLastGrabTime() {
		return lastGrabTime;
	}

	public void setLastGrabTime(Date lastGrabTime) {
		this.lastGrabTime = lastGrabTime;
	}
	
}
