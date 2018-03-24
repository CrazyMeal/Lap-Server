package com.crazymeal.montpelliermobility.data;

import java.util.List;

import com.crazymeal.montpelliermobility.domain.DocumentValidationResult;
import com.crazymeal.montpelliermobility.domain.data.ParkingData;

public interface IDataGrabber {
	
	void getSources(List<String> urlList);
	
	DocumentValidationResult validateSources();
	
	List<ParkingData> launchSources();

}
