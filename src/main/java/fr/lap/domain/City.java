package fr.lap.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = true)
	private String name;
	
	@OneToMany(targetEntity=Parking.class)
	private List<Parking> parkingList;

	public City(String name, List<Parking> parkingList) {
		super();
		this.name = name;
		this.parkingList = parkingList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parkingList == null) ? 0 : parkingList.hashCode());
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
		City other = (City) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parkingList == null) {
			if (other.parkingList != null)
				return false;
		} else if (!parkingList.equals(other.parkingList))
			return false;
		return true;
	}
	
	
}
