package fr.lap.data;

import java.util.List;

import fr.lap.domain.DocumentValidationResult;
import fr.lap.domain.ParkingData;

public interface IDataGrabber {
	
	void getSources(List<String> urlList);
	
	DocumentValidationResult validateSources();
	
	List<ParkingData> launchSources();

}
