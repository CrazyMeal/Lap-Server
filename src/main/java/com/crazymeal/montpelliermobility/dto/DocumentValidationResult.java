package com.crazymeal.montpelliermobility.dto;

import org.w3c.dom.Document;

import java.util.List;

public class DocumentValidationResult {
	
	private List<Document> validDocumentList;
	
	private List<Document> unValidDocumentList;

	public DocumentValidationResult(List<Document> validParkingList, List<Document> unValidParkingList) {
		super();
		this.validDocumentList = validParkingList;
		this.unValidDocumentList = unValidParkingList;
	}

	public List<Document> getValidDocumentList() {
		return validDocumentList;
	}

	public List<Document> getUnValidDocumentList() {
		return unValidDocumentList;
	}

	
}
