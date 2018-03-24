package com.crazymeal.montpelliermobility.worker;

import com.crazymeal.montpelliermobility.data.IDataGrabber;
import com.crazymeal.montpelliermobility.data.montpellier.MontpellierDataGrabber;
import com.crazymeal.montpelliermobility.db.BasicParkingDataRepository;
import com.crazymeal.montpelliermobility.db.CityRepository;
import com.crazymeal.montpelliermobility.db.ParkingRepository;
import com.crazymeal.montpelliermobility.domain.city.City;
import com.crazymeal.montpelliermobility.domain.data.BasicParkingData;
import com.crazymeal.montpelliermobility.domain.data.ParkingData;
import com.crazymeal.montpelliermobility.domain.parking.Parking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@PropertySource("classpath:data-urls/montpellier.properties")
@Slf4j
public class MontpellierWorker {

	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private BasicParkingDataRepository basicParkingDataRepository;
	
	@Value("${url.root}")
	private String rootUrl;
	
	public String getRootUrl() {
		return rootUrl;
	}

	@Value("${url.suffix}")
	private String suffixUrl;
	
	@Scheduled(fixedRate = 5 * 60 * 1000)	
	public void run() throws Exception {
		List<String> urlList = this.getUrlToWorkWith();
		
		if (urlList.isEmpty()) {
			log.warn("Liste d'URL a traiter vide.");
		} else {
			log.info("Lancement du grab sur la liste d'URL, size={}", urlList.size());
			IDataGrabber grabber = new MontpellierDataGrabber();
			grabber.getSources(urlList);
			grabber.validateSources();
			List<ParkingData> parkingDataList = grabber.launchSources();
			
			if (parkingDataList.isEmpty()) {
				log.warn("Aucun donnee parking ne peut etre integre en BDD");
			} else {
				int urlListSize = urlList.size() - 1;
				int parsedParkingListSize = parkingDataList.size();
				
				if (urlListSize != parsedParkingListSize) {
					log.warn("Toutes les URLs n'ont pas pu etre parsees");
				} else {
					log.info("Toutes les URLs ont ete parsees");
				}
				
				for (ParkingData parkingData : parkingDataList) {
					if (parkingData instanceof BasicParkingData) {
						BasicParkingData bpd = (BasicParkingData) parkingData;
						log.info(bpd.toString());
						this.integrateBasicParkingData(bpd);
					}
				}
			}
		}
	}
	
	@Transactional
	public void integrateBasicParkingData(BasicParkingData bpd) {
		Parking parking = bpd.getParking();
		
		if (parking == null) {
			log.warn("Pas de parking trouve pour un groupe de donnees {}");
		} else {
			City city = parking.getCity();
			
			if (city == null) {
				log.warn("Pas de ville pour ce parking");
			} else {

				City montpellierCityFromRepository = this.cityRepository.findByName("Montpellier");
				if (montpellierCityFromRepository == null) {
					this.cityRepository.save(city);
				} else {
					parking.setCity(montpellierCityFromRepository);
				}
				
				String parkingName = parking.getName();
				Parking montpellierParkingFromRepository = this.parkingRepository.findByName(parkingName);
				if (montpellierParkingFromRepository == null) {
					this.parkingRepository.save(parking);
				} else {
					Date lastGrabTime = parking.getLastGrabTime();
					montpellierParkingFromRepository.setLastGrabTime(lastGrabTime);
					this.parkingRepository.save(montpellierParkingFromRepository);
					bpd.setParking(montpellierParkingFromRepository);
				}
				
				this.basicParkingDataRepository.save(bpd);
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
			log.warn("Impossible de construire une liste d'URL. Verifier le contenu du fichier properties");
		}
		
		return urlList;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
