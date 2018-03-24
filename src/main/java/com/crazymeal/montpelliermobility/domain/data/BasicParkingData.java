package com.crazymeal.montpelliermobility.domain.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.crazymeal.montpelliermobility.domain.parking.Parking;

@Entity
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

	public BasicParkingData() {
		super();
	}
	
	public BasicParkingData(int freePlaces, int totalPlaces, String status, Date dateOfData, Parking parking) {
		super();
		this.freePlaces = freePlaces;
		this.totalPlaces = totalPlaces;
		this.status = status;
		this.dateOfData = dateOfData;
		this.parking = parking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BasicParkingData other = (BasicParkingData) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicParkingData [freePlaces=" + freePlaces + ", totalPlaces=" + totalPlaces + ", status=" + status
				+ ", dateOfData=" + dateOfData + ", parking=" + parking + "]";
	}

	public int getFreePlaces() {
		return freePlaces;
	}

	public int getTotalPlaces() {
		return totalPlaces;
	}

	public String getStatus() {
		return status;
	}

	public Date getDateOfData() {
		return dateOfData;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}
	
}
