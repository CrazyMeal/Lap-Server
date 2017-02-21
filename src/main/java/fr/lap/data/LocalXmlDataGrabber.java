package fr.lap.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.lap.domain.DocumentValidationResult;
import fr.lap.domain.Parking;

public class LocalXmlDataGrabber implements IDataGrabber{
	
	private List<Document> xmlDocuments;
	
	private Source schemaFile;
	
	private DocumentValidationResult validationResult;

	public void getSources(List<String> urlList) {
		this.xmlDocuments = new ArrayList<Document>();
		
		try {
			
			for (String url : urlList) {
				DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				File fileFromUrl = new File(url);
				Document document = parser.parse(fileFromUrl);
				
				if (url.contains(".")) {
					String extension = url.substring(url.lastIndexOf("."));
					
					if (extension.equals(".xsd")) {
						this.schemaFile = new StreamSource(fileFromUrl);
					} else {
						document.normalize();
						this.xmlDocuments.add(document);
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DocumentValidationResult validateSources() {
	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    
		try {
			if (this.schemaFile != null) {
				List<Document> validParkingList = new ArrayList<Document>();
				List<Document> unValidParkingList = new ArrayList<Document>();
				
				Schema schema = factory.newSchema(this.schemaFile);
				
			    Validator validator = schema.newValidator();
			    
			    for (Document doc : this.xmlDocuments) {
			    	try {
						validator.validate(new DOMSource(doc));
						validParkingList.add(doc);
					} catch (SAXException e) {
						e.printStackTrace();
						
						unValidParkingList.add(doc);
					}
			    }
			    
			    this.validationResult = new DocumentValidationResult(validParkingList, unValidParkingList);
			} else {
				System.out.println("Pas de fichier XSD pour valider la structure XML");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		
		return this.validationResult;
	}

	public List<Parking> launchSources() {
		List<Parking> parkingList = new ArrayList<Parking>();
		
		for (Document document : this.xmlDocuments) {
			NodeList nList = document.getElementsByTagName("park");
		    
		    for (int id = 0 ; id < nList.getLength() ; id++) {
		    	Node node = nList.item(id);
		    	
		    	if (node.getNodeType() == Node.ELEMENT_NODE) {

		    		Element eElement = (Element) node;
					
					eElement.getElementsByTagName("DateTime").item(0).getTextContent();
					
					String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
					String status = eElement.getElementsByTagName("Status").item(0).getTextContent();
					int freePlaces = Integer.valueOf(eElement.getElementsByTagName("Free").item(0).getTextContent());
					int totalPlaces = Integer.valueOf(eElement.getElementsByTagName("Total").item(0).getTextContent());
					
					DateTime nowDateTime = new DateTime();
					
					Parking p = new Parking(freePlaces, totalPlaces, nowDateTime.toDate());
					p.setName(name);
					p.setStatus(status);
					parkingList.add(p);
				}
		    }
		}
		
		return parkingList;
	}

}
