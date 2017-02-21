package fr.lap.db;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import fr.lap.db.ParkingRepository;
import fr.lap.domain.Parking;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private ParkingRepository repository;
	
	@Test
	public void testOnParkingRepository() {
		DateTime nowDateTime = new DateTime();
		Parking persistParking = new Parking(20, 100, nowDateTime.toDate());
		persistParking.setName("Mon parking");
		
		this.entityManager.persist(persistParking);
		Parking p = this.repository.findOne(1L);
		
		assertEquals("Mon parking", p.getName());
	}
}
