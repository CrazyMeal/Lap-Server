package fr.lap.domain.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import fr.lap.domain.city.City;

public class CityTest {
	
	@Test
	public void cityEqualityTest() {
		City defaultCity1 = new City();
		City simpleCity1 = new City("city");
		City simpleCity2 = new City("City1");
		City nullCity1 = null;
		
		Object nullObject = null;
		String s = "a String";
		
		assertEquals(defaultCity1, defaultCity1);
		
		assertNotEquals(defaultCity1, nullObject);
		
		assertNotEquals(simpleCity1, simpleCity2);
		
		assertNotEquals(defaultCity1, simpleCity1);
		
		assertNotEquals(defaultCity1, nullCity1);
		
		assertNotEquals(defaultCity1, s);
	}
	
	@Test
	public void cityHashCodeTest() {
		// Test with name
		City simpleCity1 = new City("City1");
		City simpleCity1Bis = new City("City1");
		
		int hash1 = simpleCity1.hashCode();
		int hash2 = simpleCity1Bis.hashCode();
		
		assertEquals(hash1, hash2);
		
		// Test default constructor
		City defaultCity1 = new City();
		City defaultCity1Bis = new City();
		
		int hash3 = defaultCity1.hashCode();
		int hash4 = defaultCity1Bis.hashCode();
		
		assertEquals(hash3, hash4);
	}
}
