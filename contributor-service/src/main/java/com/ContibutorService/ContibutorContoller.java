package com.ContibutorService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ContibutorService.models.Beneficiary;
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
    
    @Autowired
    private Environment env;

    
    @Autowired
    private MailBuilder mailBuilder;
    
	@PostMapping("/addDonor")
    public void addDonor(@RequestBody Donor donor) throws GeneralSecurityException, IOException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	String range = "Donor!A:F";
    	
    	Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
 
    	ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
    	List<List<Object>> values = response.getValues();
    	Beneficiary beneficiary = getBeneficiary();
    	values = Arrays.asList(
    	        Arrays.asList(
    	        		beneficiary==null?"":beneficiary.getName(),beneficiary==null?"":beneficiary.getContact(),
    	        				beneficiary==null?"":beneficiary.getAddress(),"","DL"+values.size(),donor.getName(),
    	        				donor.getContact(),donor.getEmail(),donor.getCity()
    	        )
    	);
    	
    	ValueRange body = new ValueRange().setValues(values);
    	        service.spreadsheets().values().append(spreadsheetId, range, body)
    	                .setValueInputOption("USER_ENTERED")
    	                .execute();
    	if(beneficiary!=null) {
    		mailBuilder.sendMail(env.getProperty("spring.mail.username"), donor.getEmail(), beneficiary);
    	}    	
    }
    
    public Beneficiary getBeneficiary() throws GeneralSecurityException, IOException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
    								.setApplicationName(APPLICATION_NAME)
    								.build();
    	
    	String range = "Beneficiary Database!A2:Q";
    	String updateRange = "Beneficiary Database!N";
    	ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
    	List<List<Object>> values = response.getValues();
    	System.out.println(values.get(0).get(16));
    	Beneficiary beneficiary = null;
    	
    	for(int i=0;i<values.size();i++) {
    		if(values.get(i).get(13).equals("TRUE") && values.get(i).get(16).equals("FALSE")) {
    			beneficiary = new Beneficiary(values.get(i).get(3).toString(), values.get(i).get(4).toString(), values.get(i).get(5).toString());
    			updateRange+=""+(i+2);
    			values = Arrays.asList(
    	    	        Arrays.asList(
    	    	                "FALSE"
    	    	        )
    	    	);
    			ValueRange body = new ValueRange().setValues(values);
    			Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, updateRange, body);
    			request.setValueInputOption("USER_ENTERED");
    			request.execute();
    			updateRange = "Beneficiary Database!Q"+(i+2);
    			values = Arrays.asList(
    	    	        Arrays.asList(
    	    	                "TRUE"
    	    	        )
    	    	);
    			body = new ValueRange().setValues(values);
    			request = service.spreadsheets().values().update(spreadsheetId, updateRange, body);
    			request.setValueInputOption("USER_ENTERED");
    			request.execute();
    			break;
    		}
    	}
    	return beneficiary;
		
    }
}