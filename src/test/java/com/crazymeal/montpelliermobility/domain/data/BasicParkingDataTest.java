package com.crazymeal.montpelliermobility.domain.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;

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
		
		int hash1 = bpd1.hashCode();
		int hash2 = bpd2.hashCode();
		
		assertEquals(hash1, hash2);
	}
	
	@Test
	public void equalityTest() {
		BasicParkingData bpd1 = new BasicParkingData(0, 1, "ouvert", null, null);
		BasicParkingData bpd2 = new BasicParkingData(0, 1, "ouvert", null, null);
		
		Object nullObject = null;
		String s = "A String";
		
		assertEquals(bpd1, bpd1);
		assertNotEquals(bpd1, nullObject);
		assertNotEquals(bpd1, s);
		assertEquals(bpd1, bpd2);
	}
}
