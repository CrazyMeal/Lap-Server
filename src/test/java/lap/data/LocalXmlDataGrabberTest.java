package lap.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lap.model.Parking;

public class LocalXmlDataGrabberTest {
	
	@Test
	public void everythingIsOkay() {
		List<String> urlList = new ArrayList<String>();
		String localResourcesFolder = "src\\test\\resources\\localXml\\";
		String urlParking1 = localResourcesFolder + "FR_MTP_ANTI.xml";
		String urlParking2 = localResourcesFolder + "FR_MTP_ARCT.xml";
		String urlParking3 = localResourcesFolder + "FR_MTP_COME.xml";
		String urlParking4 = localResourcesFolder + "FR_MTP_CORU.xml";
		String urlParking5 = localResourcesFolder + "FR_MTP_EURO.xml";
		String urlOfXsd = localResourcesFolder + "ParkingTpsReel_Description.xsd";

		urlList.add(urlParking1);
		urlList.add(urlParking2);
		urlList.add(urlParking3);
		urlList.add(urlParking4);
		urlList.add(urlParking5);
		urlList.add(urlOfXsd);

		LocalXmlDataGrabber grabber = new LocalXmlDataGrabber();
		grabber.getSources(urlList);
		grabber.validateSources();
		
		List<Parking> parkingList = grabber.launchSources();
		assertNotNull(parkingList);
		assertEquals(5,parkingList.size());
	}
}
