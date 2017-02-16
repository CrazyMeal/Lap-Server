package lap.db;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import lap.db.ParkingRepository;
import lap.model.Parking;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private ParkingRepository repository;
	
	@Test
	public void testOnParkingRepository() {
		this.entityManager.persist(new Parking( "Mon parking", "Open", 100, 0, null, 0));
		Parking p = this.repository.findOne(1L);
		
		assertEquals("Mon parking", p.getName());
	}
}
