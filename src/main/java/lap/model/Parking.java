package lap.model;

import java.io.Serializable;
import java.util.Date;

public class Parking implements Serializable {
	
	private static final long serialVersionUID = -40937357323169826L;
	
	private Date dataTime;
	
	private String name;
	
	private String status;
	
	private int freePlaces;
	
	private int totalPlaces;
	
	private int display;
	
	public Parking(Date dataTime, String name, String status, int freePlaces, int totalPlaces, int display) {
		super();
		this.dataTime = dataTime;
		this.name = name;
		this.status = status;
		this.freePlaces = freePlaces;
		this.totalPlaces = totalPlaces;
		this.display = display;
	}

	@Override
	public String toString() {
		return "Parking [dataTime=" + dataTime + ", name=" + name + ", status=" + status + ", freePlaces=" + freePlaces
				+ ", totalPlaces=" + totalPlaces + ", display=" + display + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataTime == null) ? 0 : dataTime.hashCode());
		result = prime * result + display;
		result = prime * result + freePlaces;
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
		if (dataTime == null) {
			if (other.dataTime != null)
				return false;
		} else if (!dataTime.equals(other.dataTime))
			return false;
		if (display != other.display)
			return false;
		if (freePlaces != other.freePlaces)
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

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
