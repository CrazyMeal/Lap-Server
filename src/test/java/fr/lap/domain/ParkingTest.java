package fr.lap.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ParkingTest {
	
	@Test
	public void parkingHashTest () {
		Parking parking1 = new Parking("Parking1", null, null);
		Parking parking2 = new Parking("Parking1", null, null);
		
		Parking complexParking = new Parking("Parking2", null, null);
		
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
		Parking complexParking = new Parking("Parking2", null, null);
		Object obj = null;
		String s = "A String";
		
		assertEquals(simpleParking1, simpleParking1);
		assertNotEquals(simpleParking1, obj);
		assertNotEquals(simpleParking1, s);
		assertEquals(simpleParking1, simpleParking2);
		assertNotEquals(simpleParking1, complexParking);
		
	}
}
