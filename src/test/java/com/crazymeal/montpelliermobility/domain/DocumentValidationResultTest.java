package com.crazymeal.montpelliermobility.domain;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

public class DocumentValidationResultTest {

	@Test
	public void constructorTest() {
		List<Document> validList = new ArrayList<>();
		List<Document> unValidList = new ArrayList<>();
		
		DocumentValidationResult dvr = new DocumentValidationResult(validList, unValidList);
		assertNotNull(dvr.getValidDocumentList());
		assertNotNull(dvr.getUnValidDocumentList());
	}
}
