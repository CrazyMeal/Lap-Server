package lap.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Parking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int freePlaces;
	
	@Column(nullable = false)
	private int totalPlaces;
	
	@Column(nullable = false, columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date grabTime;
	
	@Column(nullable = true)
	private String status;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true, columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfDatas;
	
	@OneToOne(targetEntity=City.class)
	private City city;

	public Parking(int freePlaces, int totalPlaces, Date grabTime) {
		super();
		this.freePlaces = freePlaces;
		this.totalPlaces = totalPlaces;
		this.grabTime = grabTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((dateOfDatas == null) ? 0 : dateOfDatas.hashCode());
		result = prime * result + freePlaces;
		result = prime * result + ((grabTime == null) ? 0 : grabTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + totalPlaces;
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
		if (dateOfDatas == null) {
			if (other.dateOfDatas != null)
				return false;
		} else if (!dateOfDatas.equals(other.dateOfDatas))
			return false;
		if (freePlaces != other.freePlaces)
			return false;
		if (grabTime == null) {
			if (other.grabTime != null)
				return false;
		} else if (!grabTime.equals(other.grabTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (totalPlaces != other.totalPlaces)
			return false;
		return true;
	}



	public int getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}

	public int getTotalPlaces() {
		return totalPlaces;
	}

	public void setTotalPlaces(int totalPlaces) {
		this.totalPlaces = totalPlaces;
	}

	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfDatas() {
		return dateOfDatas;
	}

	public void setDateOfDatas(Date dateOfDatas) {
		this.dateOfDatas = dateOfDatas;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	
	
}
