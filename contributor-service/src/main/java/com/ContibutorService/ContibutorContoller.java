package com.ContibutorService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ContibutorService.models.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

@CrossOrigin("*")
@RestController
public class ContibutorContoller {
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	
    private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";
    
    
    @PostMapping("/addDonor")
    public void addDonor(@RequestBody Donor donor) throws GeneralSecurityException, IOException {
    	System.out.println(donor.getName());
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	final String range = "Copy of Donor!C3:F143";
    	Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    	
    	List<List<Object>> values = Arrays.asList(
    	        Arrays.asList(
    	                donor.getName(),donor.getEmail(),donor.getContact(),donor.getCity()
    	        )
    	);
    	
    	ValueRange body = new ValueRange().setValues(values);
    	AppendValuesResponse result =
    	        service.spreadsheets().values().append(spreadsheetId, range, body)
    	                .setValueInputOption("RAW")
    	                .execute();

    	
    }
}