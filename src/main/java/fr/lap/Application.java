package fr.lap;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(Application.class);
	}
}
