package com.ContibutorService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ContibutorService.models.Beneficiary;
import com.ContibutorService.models.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;


@CrossOrigin("*")
@RestController
public class AutoDonorAssigner {
	
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";
    
    @Autowired
    private MailBuilder mailBuilder;
    
    @Autowired
    private Environment env;
	
	@GetMapping("/syncDonorBeneficiary")
	public void syncDonorBeneficiary() throws GeneralSecurityException, IOException, InterruptedException {
		
		System.out.println("called");
		
//		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//		String range = "Copy of Donor!A3:N";
//		
//		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//		
//		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
//    	List<List<Object>> allDonors = response.getValues();
//    	System.out.println(allDonors.get(0).size());
//    	Map<Integer, Donor> donorMap = new HashMap<Integer, Donor>();
//    	
//    	for(int i=0;i<allDonors.size();i++) {
//    		if(allDonors.get(i).get(0).equals("") && allDonors.get(i).get(1).equals("") && allDonors.get(i).get(2).equals("")) {
//    			donorMap.put(i, new Donor(allDonors.get(i).get(5).toString(), 
//    									  allDonors.get(i).get(6).toString(), 
//    					                  allDonors.get(i).get(7).toString(), 
//    					                  allDonors.get(i).get(8).toString()));
//    		}
//    	}
//    	int count = 0;
//    	for(int i=0;i<allDonors.size();i++) {
//    		if(!allDonors.get(i).get(0).equals("") && !allDonors.get(i).get(1).equals("") && !allDonors.get(i).get(2).equals("")) {
//    			count++;
//    		}
//    	}
//    	
//    	range = "Copy of Beneficiary Database 1!A3:Q";
//    	response = service.spreadsheets().values().get(spreadsheetId, range).execute();
//    	List<List<Object>> allBeneficiary = response.getValues();
//    	Map<Integer, Beneficiary> beneficiaryMap = new HashMap<Integer, Beneficiary>();
//    	
//    	for(int i=0;i<allBeneficiary.size();i++) {
//    		if(allBeneficiary.get(i).get(13).equals("TRUE") && allBeneficiary.get(i).get(16).equals("FALSE")) {
//    			beneficiaryMap.put(i, new Beneficiary(allBeneficiary.get(i).get(3).toString(), 
//    					                              allBeneficiary.get(i).get(4).toString(), 
//    					                              allBeneficiary.get(i).get(5).toString()));
//    		}
//    	}
//    	System.out.println(beneficiaryMap);
//    	if(beneficiaryMap.size()==0 || donorMap.size()==0) {return;}
//    	
//    	int donIndex = count;
//    	int size = Math.min(donorMap.size(), beneficiaryMap.size());
//    		for(Map.Entry<Integer, Beneficiary> entry : beneficiaryMap.entrySet()) {
//    			if(size==0) {return;}
//    			List<List<Object>> values = Arrays.asList(
//    					Arrays.asList(
//    	    	                "TRUE"
//    	    	        ) 
//    	    	);
//    			String updateRange = "Copy of Beneficiary Database 1!Q"+(entry.getKey()+3);
//    			ValueRange body = new ValueRange().setValues(values);
//    			Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, updateRange, body);
//    			request.setValueInputOption("USER_ENTERED");
//    			request.execute();
//    			
//    			
//    			values = Arrays.asList(
//    	    	        Arrays.asList(
//    	    	        		entry.getValue().getName(),
//    	    	        		entry.getValue().getContact(),
//    	    	        		entry.getValue().getAddress()
//    	    	        )
//    	    	);
//    			
//    			updateRange = "Copy of Donor!A"+(donIndex+3)+":C";
//    			
//    			body = new ValueRange().setValues(values);
//    			request = service.spreadsheets().values().update(spreadsheetId, updateRange, body);
//    			request.setValueInputOption("USER_ENTERED");
//    			request.execute();
//    			
//    			
//    			System.out.println(donorMap.get(donIndex).getEmail());
//    			
//    			
//    			
//    			/*mailBuilder.sendMail(env.getProperty("spring.mail.username"), donorMap.get(donIndex).getEmail(), 
//    								 new Beneficiary(entry.getValue().getName(),
//    	        					                 entry.getValue().getContact(),
//    	        									 entry.getValue().getAddress()));
//    	        									 */
//    			donIndex++;
//    			size--;
//    			Thread.sleep(2000);
//    		}
	}

}
