package fr.lap.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ParkingTest {
	
	@Test
	public void constructorTest() {
		Parking parking = new Parking("Mon parking", new City(), null);
		
		assertEquals("Mon parking", parking.getName());
		assertNotNull(parking.getCity());
	}
	
	@Test
	public void parkingHashTest () {
		Parking parking1 = new Parking("Parking1", null, null);
		Parking parking2 = new Parking("Parking1", null, null);
		
		Parking complexParking = new Parking(null, new City(), null);
		
		int hash1 = parking1.hashCode();
		int hash2 = parking2.hashCode();
		int hashOfComplexCity = complexParking.hashCode();
		
		assertEquals(hash1, hash2);
		assertNotEquals(hash1, hashOfComplexCity);
	}

	@Test
	public void parkingEqualityTest() {
		Parking simpleParking1 = new Parking("Parking1", null, null);
		Parking simpleParking2 = new Parking("Parking1", null, null);
		Parking complexParking = new Parking("Parking2", new City(), null);
		Parking complexParking2 = new Parking("Parking2", new City(), null);
		Parking unNamedParking = new Parking(null, null, null);
		Parking unNamedParking2 = new Parking(null, null, null);
		
		Object obj = null;
		String s = "A String";
		
		assertEquals(simpleParking1, simpleParking1);
		assertNotEquals(simpleParking1, obj);
		assertNotEquals(simpleParking1, s);
		assertEquals(simpleParking1, simpleParking2);
		assertNotEquals(simpleParking1, complexParking);
		assertNotEquals(complexParking, simpleParking1);
		assertEquals(complexParking, complexParking2);
		
		assertNotEquals(unNamedParking, simpleParking1);
		assertNotEquals(simpleParking1, unNamedParking);
		assertEquals(unNamedParking, unNamedParking2);
	}
}
