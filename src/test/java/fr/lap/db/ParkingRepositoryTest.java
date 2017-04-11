package fr.lap.db;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import fr.lap.db.ParkingRepository;
import fr.lap.domain.city.City;
import fr.lap.domain.parking.Parking;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingRepositoryTest {
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private ParkingRepository repository;
	
	@Test
	public void testOnParkingRepository() {
		City c = new City("CityTest");
		DateTime nowDateTime = new DateTime();
		Parking persistParking = new Parking("Mon parking", c, nowDateTime.toDate());
		
		this.entityManager.persist(c);
		this.entityManager.persist(persistParking);
		
		Parking p = this.repository.findOne(1L);
		
		assertEquals("Mon parking", p.getName());
	}
}
