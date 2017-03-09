package fr.lap.domain.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import fr.lap.domain.data.BasicParkingData;

public class BasicParkingDataTest {

	@Test
	public void constructorTest() {
		BasicParkingData bpd = new BasicParkingData(0, 1, "ouvert", null, null);
		
		assertEquals(0, bpd.getFreePlaces());
		assertEquals(1, bpd.getTotalPlaces());
		assertEquals("ouvert", bpd.getStatus());
		assertNull(bpd.getDateOfData());
		assertNull(bpd.getParking());
		
		BasicParkingData basicBpd = new BasicParkingData();
		assertEquals(0, basicBpd.getFreePlaces());
		assertEquals(0, basicBpd.getTotalPlaces());
		assertNull(basicBpd.getStatus());
		assertNull(basicBpd.getParking());
		assertNull(basicBpd.getDateOfData());
	}
	
	@Test
	public void hashCodeTest() {
		BasicParkingData bpd1 = new BasicParkingData(0, 1, "ouvert", null, null);
		BasicParkingData bpd2 = new BasicParkingData(0, 1, "ouvert", null, null);
		BasicParkingData bpd3 = new BasicParkingData(0, 1, "ouvert", null, null);
		bpd3.setId(1L);
		
		int hash1 = bpd1.hashCode();
		int hash2 = bpd2.hashCode();
		int hash3 = bpd3.hashCode();
		
		assertEquals(hash1, hash2);
		assertNotEquals(hash1, hash3);
	}
	
	@Test
	public void equalityTest() {
		Object nullObject = null;
		BasicParkingData bpd1 = new BasicParkingData(0, 1, "ouvert", null, null);
		String s = "A String";
		
		BasicParkingData bpd2 = new BasicParkingData(0, 1, "ouvert", null, null);
		bpd2.setId(1L);
		BasicParkingData bpd3 = new BasicParkingData(0, 1, "ouvert", null, null);
		bpd3.setId(2L);
		BasicParkingData bpd4 = new BasicParkingData(0, 1, "ouvert", null, null);
		bpd4.setId(2L);
		
		BasicParkingData bpd5 = new BasicParkingData(0, 1, "ouvert", null, null);
		
		assertEquals(bpd1, bpd1);
		assertNotEquals(bpd1, nullObject);
		assertNotEquals(bpd1, s);
		assertNotEquals(bpd1, bpd2);
		assertNotEquals(bpd2, bpd3);
		assertEquals(bpd3, bpd4);
		assertEquals(bpd1, bpd5);
	}
}
