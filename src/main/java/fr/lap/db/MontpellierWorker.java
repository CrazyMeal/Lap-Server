package fr.lap.db;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import fr.lap.data.IDataGrabber;
import fr.lap.data.montpellier.MontpellierDataGrabber;
import fr.lap.domain.City;
import fr.lap.domain.Parking;

@Component
@PropertySource("classpath:data-urls/montpellier.properties")
public class MontpellierWorker implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MontpellierWorker.class);
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Value("${url.root}")
	private String rootUrl;
	
	@Value("${url.suffix}")
	private String suffixUrl;
	
	@Override
	public void run(String... arg0) throws Exception {
		List<String> urlList = this.getUrlToWorkWith();
		
		if (urlList.isEmpty()) {
			LOGGER.warn("Liste d'URL a traiter vide.");
		} else {
			LOGGER.info("Lancement du grab sur la liste d'URL, size={}", urlList.size());
			IDataGrabber grabber = new MontpellierDataGrabber();
			grabber.getSources(urlList);
			grabber.validateSources();
			List<Parking> parkingList = grabber.launchSources();
			
			if (parkingList.isEmpty()) {
				LOGGER.warn("Aucun parking ne peut être integre en BDD");
			} else {
				int urlListSize = urlList.size();
				int parsedParkingListSize = parkingList.size();
				
				if (urlListSize != parsedParkingListSize) {
					LOGGER.warn("Toutes les URLs n'ont pas pu etre parsees");
				} else {
					LOGGER.info("Toutes les URLs ont ete parsees");
				}
				
				City montpellierCity = cityRepository.findByName("Montpellier");
				
				if (montpellierCity == null) {
					LOGGER.info("La ville de Montpellier n'existe pas en base -> Creation");
					montpellierCity = new City("Montpellier");
				}
				
				for (Parking parking : parkingList) {
					parking.setCity(montpellierCity);
				}
				
				this.cityRepository.save(montpellierCity);
				this.parkingRepository.save(parkingList);
			}
		}
	}
	
	public List<String> getUrlToWorkWith() {
		List<String> urlList = new ArrayList<String>();
		
		if (this.rootUrl != null && this.suffixUrl != null) {
			String[] suffixList = this.suffixUrl.split(";");
			
			for (String suffix : suffixList) {
				StringBuilder sb = new StringBuilder();
				sb.append(this.rootUrl);
				sb.append(suffix);
				
				urlList.add(sb.toString());
			}
		} else {
			LOGGER.warn("Impossible de construire une liste d'URL. Verifier le contenu du fichier properties");
		}
		
		return urlList;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
