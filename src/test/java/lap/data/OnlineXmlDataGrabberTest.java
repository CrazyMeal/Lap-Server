package lap.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lap.model.Parking;

public class OnlineXmlDataGrabberTest {

	@Test
	public void everythingIsOk() {
		List<String> listeUrl = new ArrayList<String>();
		String rootUrl = "http://data.montpellier3m.fr/sites/default/files/ressources/";
		String url1 = rootUrl + "FR_MTP_ANTI.xml";
		String url2 = rootUrl + "FR_MTP_ARCT.xml";
		String url3 = rootUrl + "FR_MTP_COME.xml";
		String url4 = rootUrl + "FR_MTP_CORU.xml";
		String url5 = rootUrl + "FR_MTP_EURO.xml";
		String url6 = rootUrl + "FR_MTP_FOCH.xml";
		String url7 = rootUrl + "FR_MTP_GAMB.xml";
		String url8 = rootUrl + "FR_MTP_GARE.xml";
		String url9 = rootUrl + "FR_MTP_TRIA.xml";
		String url10 = rootUrl + "ParkingTpsReel_Description.xsd";
		
		
		listeUrl.add(url2);
		listeUrl.add(url3);
		listeUrl.add(url4);
		listeUrl.add(url5);
		listeUrl.add(url6);
		listeUrl.add(url7);
		listeUrl.add(url8);
		listeUrl.add(url9);
		listeUrl.add(url10);
		listeUrl.add(url1);
		
		OnlineXmlDataGrabber grabber = new OnlineXmlDataGrabber();
		grabber.getSources(listeUrl);
		grabber.validateSources();
		List<Parking> parkingList = grabber.launchSources();
		
		assertNotNull(parkingList);
		assertNotEquals(0, parkingList.size());
		
	}
}
