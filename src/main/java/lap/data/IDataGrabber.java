package lap.data;

import java.util.List;

import lap.model.DocumentValidationResult;
import lap.model.Parking;

public interface IDataGrabber {
	
	void getSources(List<String> urlList);
	
	DocumentValidationResult validateSources();
	
	List<Parking> launchSources();

}
