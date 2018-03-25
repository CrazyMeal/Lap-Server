package com.crazymeal.montpelliermobility.worker;

import com.crazymeal.montpelliermobility.domain.Parking;
import com.crazymeal.montpelliermobility.domain.ParkingData;
import com.crazymeal.montpelliermobility.grabber.DataGrabber;
import com.crazymeal.montpelliermobility.repository.ParkingDataRepository;
import com.crazymeal.montpelliermobility.repository.ParkingRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(name = "scrapper.parking.activated")
@Component
@PropertySource("classpath:data-urls/montpellier.properties")
@Slf4j
public class MontpellierWorker {

	@Autowired
	private ParkingRepository parkingRepository;

	@Autowired
	private ParkingDataRepository basicParkingDataRepository;

	@Autowired
	private DataGrabber dataGrabber;

	@Value("${url.root}")
	@Getter
	private String rootUrl;

	@Value("${url.suffix}")
	private String suffixUrl;

	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void run() throws Exception {
		List<String> urlList = this.getUrlToWorkWith();

		if (urlList.isEmpty()) {
			log.warn("Liste d'URL a traiter vide.");
		} else {
			log.info("Lancement du grab sur la liste d'URL, size={}", urlList.size());
			dataGrabber.getSources(urlList);
			dataGrabber.validateSources();
			List<ParkingData> parkingDataList = dataGrabber.launchSources();

			if (parkingDataList.isEmpty()) {
				log.warn("Aucun donnee parking ne peut etre integre en BDD");
			} else {
				// We don't take into account the schema URL
				int urlListSize = urlList.size() - 1;
				int parsedParkingListSize = parkingDataList.size();

				if (urlListSize != parsedParkingListSize) {
					log.warn("Toutes les URLs n'ont pas pu etre parsees");
				} else {
					log.info("Toutes les URLs ont ete parsees");
				}

				parkingDataList.forEach(parkingData -> {
					log.info(parkingData.toString());
					this.integrateBasicParkingData(parkingData);
				});
			}
		}
	}

	private List<String> getUrlToWorkWith() {
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

	private void integrateBasicParkingData(ParkingData parkingData) {

		this.parkingRepository.findByTechnicalName(parkingData.getName())
			.switchIfEmpty(this.parkingRepository.save(Parking.builder()
					.name("The long name")
					.technicalName(parkingData.getName())
					.lastUpdated(new DateTime())
					.status(parkingData.getStatus())
					.freePlaces(parkingData.getFreePlaces())
					.totalPlaces(parkingData.getTotalPlaces())
					.build())
			)
			.flatMap(parking -> {
				parking.setStatus(parkingData.getStatus());
				parking.setFreePlaces(parkingData.getFreePlaces());
				parking.setTotalPlaces(parkingData.getTotalPlaces());
				parking.setLastUpdated(new DateTime());
				return this.parkingRepository.save(parking);
			})
			.subscribe(parking -> {
				log.info("Adding new data for parking -> {}", parking);
				parkingData.setParking(parking);
				this.basicParkingDataRepository.save(parkingData)
						.subscribe(savedParkingData -> log.info("Saved new parking data", savedParkingData));
			});
		;
	}
}
