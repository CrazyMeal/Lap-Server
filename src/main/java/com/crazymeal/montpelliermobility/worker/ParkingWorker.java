package com.crazymeal.montpelliermobility.worker;

import com.crazymeal.montpelliermobility.domain.Parking;
import com.crazymeal.montpelliermobility.domain.ParkingData;
import com.crazymeal.montpelliermobility.dto.DocumentValidationResult;
import com.crazymeal.montpelliermobility.grabber.DataGrabber;
import com.crazymeal.montpelliermobility.repository.ParkingDataRepository;
import com.crazymeal.montpelliermobility.repository.ParkingRepository;
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
@PropertySource("classpath:data-urls/parkings.properties")
@Slf4j
public class ParkingWorker {

	private final ParkingRepository parkingRepository;
	private final ParkingDataRepository parkingDataRepository;
	private final DataGrabber dataGrabber;

	@Value("${url.root}")
	private String rootUrl;

	@Value("${url.suffix}")
	private String suffixUrl;

	@Autowired
	public ParkingWorker(ParkingRepository parkingRepository, ParkingDataRepository parkingDataRepository, DataGrabber dataGrabber) {
		this.parkingRepository = parkingRepository;
		this.parkingDataRepository = parkingDataRepository;
		this.dataGrabber = dataGrabber;
	}

	@Scheduled(cron = "${scrapper.parking.cron}")
	public void scrapParkingDatas() throws Exception {
		List<String> urlList = this.getUrlToWorkWith();

		if (urlList.isEmpty()) {
			log.warn("URL list is empty, check properties of application");
		} else {
			log.info("Beggining scrapping for {} URLs", urlList.size());

			dataGrabber.loadSources(urlList);

			final DocumentValidationResult documentValidationResult = dataGrabber.validateSources();
			if (documentValidationResult.getValidDocumentList().isEmpty()) {
				log.warn("No document could be validated");
			}

			final List<ParkingData> parkingDataList = dataGrabber.launchSources();
			if (parkingDataList.isEmpty()) {
				log.warn("No data could be loaded");
			} else {
				// We don't take into account the schema URL
				final int urlListSize = urlList.size() - 1;
				final int parsedParkingListSize = parkingDataList.size();

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

		this.dataGrabber.clean();
	}

	private List<String> getUrlToWorkWith() {
		List<String> urlList = new ArrayList<>();

		final String[] suffixList = this.suffixUrl.split(";");

		for (String suffix : suffixList) {
			StringBuilder sb = new StringBuilder();
			sb.append(this.rootUrl);
			sb.append(suffix);

			urlList.add(sb.toString());
		}

		return urlList;
	}

	/**
	 * Integrate the new data into database and update parking (create if not existing)
	 * @param parkingData
	 */
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
				this.parkingDataRepository.save(parkingData)
						.subscribe(savedParkingData -> log.info("Saved new parking data", savedParkingData));
			});
	}
}
