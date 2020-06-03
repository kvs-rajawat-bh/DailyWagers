package com.ContibutorService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

public class UpdateSheet {
	
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";

	public void update(List<List<Object>> values, String range) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
    								.setApplicationName(APPLICATION_NAME)
    								.build();
		ValueRange body = new ValueRange().setValues(values);
		Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, range, body);
		request.setValueInputOption("USER_ENTERED");
		request.execute();
	}

}
