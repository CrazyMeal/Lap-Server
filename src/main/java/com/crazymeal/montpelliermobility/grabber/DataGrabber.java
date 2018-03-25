package com.crazymeal.montpelliermobility.grabber;

import com.crazymeal.montpelliermobility.dto.DocumentValidationResult;
import com.crazymeal.montpelliermobility.domain.ParkingData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataGrabber {

	private List<Document> xmlDocuments;

	@Getter
	private Schema schema;
	
	private DocumentValidationResult validationResult;

	/**
	 * {@inheritDoc}
	 */
	public void getSources(List<String> urlList) {
		this.xmlDocuments = new ArrayList<Document>();

		urlList.forEach((urlString) -> {
			try {
				URL url = new URL(urlString);

				String extension = urlString.substring(urlString.lastIndexOf("."));

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
	 * {@inheritDoc}
	 */
	public DocumentValidationResult validateSources() {
		try {
			if (this.schema != null) {
				List<Document> validParkingList = new ArrayList<Document>();
				List<Document> unValidParkingList = null;

				Validator validator = this.schema.newValidator();

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
				log.warn("Pas de source XSD pour valider la structure XML");
			}
		} catch (IOException e) {
			log.error("Some IO error encountered", e);
		}

		return this.validationResult;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ParkingData> launchSources() {
		List<ParkingData> parkingDataList = new ArrayList<ParkingData>();
		DateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		this.validationResult.getValidDocumentList().forEach(document -> {
			NodeList nList = document.getElementsByTagName("park");

			ParkingData parkingData = MontpellierDataUtils.extractParkingDataFromXml(nList, parser);
			if (parkingData != null) {
				parkingDataList.add(parkingData);
			}
		});

		return parkingDataList;
	}

	private void grabXmlFile(URL url) {
		try {
			InputStream stream = url.openStream();

			if (stream.available() > 0) {
				DocumentBuilder parser = null;
				try {
					parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					log.error("Failed to instanciate parser", e);
				}

				try {
					Document document = parser.parse(stream);
					document.normalize();
					this.xmlDocuments.add(document);

				} catch (SAXException e) {
					log.error("Failed to parse document", e);
				}

			} else {
				log.error("Pas de data a lire sur l'url du XML - url> " + url.toString());
			}

			stream.close();

		} catch (IOException e) {
			log.error("Got network trouble", e);
		}
	}

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
				log.error("Pas de data a lire sur l'url du schema XSD - url> " + url.toString());
			}

			stream.close();
		} catch (IOException e) {
			log.error("Couldn't get schema", e);
		}
	}
}
