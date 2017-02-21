package fr.lap;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import fr.lap.db.ParkingRepository;

@SpringBootApplication
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(final ParkingRepository repository) {
		return (args) -> {
//			// save a couple of customers
//			repository.save(new Parking(null, "Parking1", null, 100, 200, 0));
//			repository.save(new Parking(null, "Parking2", null, 100, 200, 0));
//			repository.save(new Parking(null, "Parking3", null, 100, 200, 0));
//
//			// fetch all customers
//			log.info("Parkings found with findAll():");
//			log.info("-------------------------------");
//			for (Parking customer : repository.findAll()) {
//				log.info(customer.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Parking customer = repository.findOne(1L);
//			log.info("Parking found with findOne(1L):");
//			log.info("--------------------------------");
//			log.info(customer.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Parking found with findByName('Parking1'):");
//			log.info("--------------------------------------------");
//			for (Parking bauer : repository.findByName("Parking1")) {
//				log.info(bauer.toString());
//			}
//			log.info("");
		};
	}

}
