package com.crazymeal.montpelliermobility.grabber;

import com.crazymeal.montpelliermobility.domain.ParkingData;
import com.crazymeal.montpelliermobility.dto.DocumentValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DataGrabber {

	private List<Document> xmlDocuments;
	private Schema schema;
	private DocumentValidationResult validationResult;

	private final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	/**
	 * Load sources from given URLs
	 * @param urlList
	 */
	public void loadSources(List<String> urlList) {
		this.xmlDocuments = new ArrayList<>();

		urlList.forEach((urlString) -> {
			try {
				URL url = new URL(urlString);

				final String extension = urlString.substring(urlString.lastIndexOf("."));

				if (extension.equals(".xsd")) {
					this.grabXsdFile(url);
				} else {
					this.grabXmlFile(url);
				}

			} catch (MalformedURLException e) {
				log.error("URL was malformed", e);
			}
		});
	}

	/**
	 * Validate previously downloaded documents with XSD
	 * @return a DTO with validated and not validated documents
	 */
	public DocumentValidationResult validateSources() {
		try {
			if (this.schema != null) {
				List<Document> validParkingList = new ArrayList<>();
				List<Document> unValidParkingList = null;

				final Validator validator = this.schema.newValidator();

				for (Document doc : this.xmlDocuments) {
					try {
						validator.validate(new DOMSource(doc));
						validParkingList.add(doc);
					} catch (SAXException e) {
						log.error("Couldn't validate document with XSD", e);

						if (unValidParkingList == null) {
							unValidParkingList = new ArrayList<Document>();
						}

						unValidParkingList.add(doc);
					}
				}

				this.validationResult = new DocumentValidationResult(validParkingList, unValidParkingList);

			} else {
				log.warn("No XSD found to validate XML");
			}
		} catch (IOException e) {
			log.error("Some IO error encountered", e);
		}

		return this.validationResult;
	}

	/**
	 * Convert documents into objects that we can manipulate and save to database
	 * @return a list of parking data
	 */
	public List<ParkingData> launchSources() {
		List<ParkingData> parkingDataList = new ArrayList<>();

		this.validationResult.getValidDocumentList().forEach(document -> {
			final NodeList nList = document.getElementsByTagName("park");

			final ParkingData parkingData = this.extractParkingDataFromXml(nList);
			if (parkingData != null) {
				parkingDataList.add(parkingData);
			}
		});

		return parkingDataList;
	}

	/**
	 * Clean fields so we are sure that we are not using strange old data(s)
	 */
	public void clean() {
		this.schema = null;
		this.validationResult = null;
		this.xmlDocuments = null;
	}

	/**
	 * Download XML file
	 * @param url
	 */
	private void grabXmlFile(URL url) {
		try {
			InputStream stream = url.openStream();

			if (stream.available() > 0) {
				DocumentBuilder parser = null;
				try {
					parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					log.error("Failed to instanciate dateParser", e);
				}

				try {
					Document document = parser.parse(stream);
					document.normalize();
					this.xmlDocuments.add(document);

				} catch (SAXException e) {
					log.error("Failed to parse document", e);
				}

			} else {
				log.error("No data could be scrapped on {}", url.toString());
			}

			stream.close();

		} catch (IOException e) {
			log.error("Got network trouble", e);
		}
	}

	/**
	 * Download XSD file (schema)
	 * @param url
	 */
	private void grabXsdFile(URL url) {

		try {
			HttpURLConnection schemaConn = (HttpURLConnection) url.openConnection();
			InputStream stream = schemaConn.getInputStream();

			if (stream != null && stream.available() > 0) {
				StreamSource streamSource = new StreamSource(stream);
				SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				try {
					this.schema = factory.newSchema(streamSource);
				} catch (SAXException e) {
					log.error("Parsing of schema failed", e);
				}
			} else {
				log.error("No data could be scrapped on {}",  url.toString());
			}

			assert stream != null;
			stream.close();
		} catch (IOException e) {
			log.error("Couldn't get schema", e);
		}
	}

	/**
	 * Convert a document to a parking data DTO
	 * @param nList
	 * @return parking data
	 */
	private ParkingData extractParkingDataFromXml(final NodeList nList) {
		for (int id = 0; id < nList.getLength(); id++) {
			final Node node = nList.item(id);

			if (Node.ELEMENT_NODE == node.getNodeType()) {

				final Element eElement = (Element) node;

				final String dateFromFile = eElement.getElementsByTagName("DateTime").item(0).getTextContent();
				Date dateOfData = null;
				try {
					dateOfData = this.dateParser.parse(dateFromFile);
				} catch (ParseException e) {
					log.error("Error parsing date", e);
				}

				final String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
				final String status = eElement.getElementsByTagName("Status").item(0).getTextContent();
				final int freePlaces = Integer.valueOf(eElement.getElementsByTagName("Free").item(0).getTextContent());
				final int totalPlaces = Integer.valueOf(eElement.getElementsByTagName("Total").item(0).getTextContent());

				return ParkingData.builder()
						.name(name)
						.status(status)
						.freePlaces(freePlaces)
						.totalPlaces(totalPlaces)
						.dateOfData(dateOfData)
						.build();
			}
		}

		return null;
	}
}
