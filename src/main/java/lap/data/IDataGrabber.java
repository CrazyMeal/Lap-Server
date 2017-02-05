package lap.data;

import java.util.List;

import lap.model.Parking;

public interface IDataGrabber {
	
	void getSources(List<String> urlList);
	
	void validateSources();
	
	List<Parking> launchSources();

}
