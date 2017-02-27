package fr.lap.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CityTest {
	
	@Test
	public void cityEqualityTest() {
		City defaultCity1 = new City();
		City simpleCity1 = new City("city");
		City simpleCity2 = new City("City1");
		City nullCity1 = null;
		City complexCity1 = new City(0L, "City2");
		City complexCity2 = new City(1L, "City2");
		City complexCity1Bis = new City(0L, "City2");
		
		Object nullObject = null;
		String s = "a String";
		
		assertEquals(defaultCity1, defaultCity1);
		
		assertNotEquals(defaultCity1, nullObject);
		
		assertNotEquals(simpleCity1, simpleCity2);
		
		assertNotEquals(defaultCity1, simpleCity1);
		
		assertNotEquals(defaultCity1, nullCity1);
		
		assertNotEquals(defaultCity1, s);
		
		assertNotEquals(defaultCity1, complexCity1);
		
		assertNotEquals(complexCity1, complexCity2);
		
		assertEquals(complexCity1, complexCity1Bis);
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
		
		// Test id + name constructor
		City complexCity1 = new City(0L, "City1");
		City complexCity1Bis = new City(0L, "City1");
		
		int hash5 = complexCity1.hashCode();
		int hash6 = complexCity1Bis.hashCode();
		
		assertEquals(hash5, hash6);
	}
}
