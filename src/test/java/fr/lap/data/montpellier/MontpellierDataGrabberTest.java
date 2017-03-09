package fr.lap.data.montpellier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import fr.lap.domain.DocumentValidationResult;
import fr.lap.domain.data.BasicParkingData;
import fr.lap.domain.data.ParkingData;

public class MontpellierDataGrabberTest {
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}

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
		String url10 = rootUrl + "FR_MTP_PITO.xml";
		String url11 = rootUrl + "ParkingTpsReel_Description.xsd";
		
		listeUrl.add(url1);
		listeUrl.add(url2);
		listeUrl.add(url3);
		listeUrl.add(url4);
		listeUrl.add(url5);
		listeUrl.add(url6);
		listeUrl.add(url7);
		listeUrl.add(url8);
		listeUrl.add(url9);
		listeUrl.add(url10);
		listeUrl.add(url11);
		
		
		MontpellierDataGrabber grabber = new MontpellierDataGrabber();
		grabber.getSources(listeUrl);
		DocumentValidationResult validationResult = grabber.validateSources();
		List<ParkingData> parkingDataList = grabber.launchSources();
		
		assertNotNull(parkingDataList);
		assertNotEquals(0, parkingDataList.size());
		
		assertTrue(parkingDataList.get(0) instanceof BasicParkingData);
		
		assertNull(validationResult.getUnValidDocumentList());
		
		assertEquals(listeUrl.size() - 1, parkingDataList.size());
	}
	
	@Test
	public void malformedUrlTest() {
		List<String> listeUrl = new ArrayList<String>();
		String rootUrl = "htp://datamontpellier3mfr/sites/default/files/ressources/";
		
		listeUrl.add(rootUrl);
		
		MontpellierDataGrabber grabber = new MontpellierDataGrabber();
		grabber.getSources(listeUrl);
		
	}
	
	@Test
	public void noSchemaForValidationTest() {
		MontpellierDataGrabber grabber = new MontpellierDataGrabber();
		
		grabber.validateSources();
	}
}
