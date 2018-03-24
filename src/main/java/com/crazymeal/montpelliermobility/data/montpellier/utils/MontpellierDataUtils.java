package com.crazymeal.montpelliermobility.data.montpellier.utils;

import com.crazymeal.montpelliermobility.domain.city.City;
import com.crazymeal.montpelliermobility.domain.data.BasicParkingData;
import com.crazymeal.montpelliermobility.domain.parking.Parking;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@Slf4j
public class MontpellierDataUtils {

	public static BasicParkingData extractParkingDataFromXml(NodeList nList, DateFormat dateFormat, City montpellierCity) {
		for (int id = 0; id < nList.getLength(); id++) {
			Node node = nList.item(id);

			if (Node.ELEMENT_NODE == node.getNodeType()) {

				Element eElement = (Element) node;

				String dateFromFile = eElement.getElementsByTagName("DateTime").item(0).getTextContent();
				Date dateOfData = null;
				try {
					dateOfData = dateFormat.parse(dateFromFile);
				} catch (ParseException e) {
					log.error("Error parsing date", e);
				}

				String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
				String status = eElement.getElementsByTagName("Status").item(0).getTextContent();
				int freePlaces = Integer.valueOf(eElement.getElementsByTagName("Free").item(0).getTextContent());
				int totalPlaces = Integer.valueOf(eElement.getElementsByTagName("Total").item(0).getTextContent());

				DateTime nowDateTime = new DateTime();

				Parking parking = new Parking(name, montpellierCity, nowDateTime.toDate());

				return new BasicParkingData(freePlaces, totalPlaces, status, dateOfData, parking);
			}
		}

		return null;
	}
}
