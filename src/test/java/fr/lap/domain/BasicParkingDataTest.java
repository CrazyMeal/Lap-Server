package fr.lap.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BasicParkingDataTest {

	@Test
	public void constructorTest() {
		BasicParkingData bpd = new BasicParkingData(0, 1, "ouvert", null, null);
		
		assertEquals(0, bpd.getFreePlaces());
		assertEquals(1, bpd.getTotalPlaces());
		assertEquals("ouvert", bpd.getStatus());
		assertNull(bpd.getDateOfData());
		assertNull(bpd.getParking());
	}
}
