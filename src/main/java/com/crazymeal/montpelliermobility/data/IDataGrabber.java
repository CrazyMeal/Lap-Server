package com.crazymeal.montpelliermobility.data;

import com.crazymeal.montpelliermobility.domain.DocumentValidationResult;
import com.crazymeal.montpelliermobility.domain.data.ParkingData;

import java.util.List;

public interface IDataGrabber {

	/**
	 * Load different sources from given URLs
	 * @param urlList
	 */
	void getSources(List<String> urlList);

	/**
	 * Validate the sources
	 * @return a DocumentValidationResult that contains valid and unvalid documents
	 */
	DocumentValidationResult validateSources();

	/**
	 * Convert sources to object that we can manipulate
	 * @return
	 */
	List<ParkingData> launchSources();

}
