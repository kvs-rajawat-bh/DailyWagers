package com.ContibutorService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ContibutorService.Models.Beneficiary;
import com.ContibutorService.Models.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

@CrossOrigin("*")
@RestController
public class ContibutorContoller {
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";
    
    @Autowired
    private Environment env;

    
    @Autowired
    private MailBuilder mailBuilder;
    
	@PostMapping("/addDonor")
    public void addDonor(@RequestBody Donor donor) throws GeneralSecurityException, IOException, InterruptedException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	String range = "Donor!A:P";
    	
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
    						  .setInsertDataOption("INSERT_ROWS")
    						  .execute();
    	if(beneficiary!=null) {
    		mailBuilder.sendMail(env.getProperty("spring.mail.username"), donor.getEmail(), beneficiary, null, false);
    	}    	
	}
	

	 ///////////////////////////////////////Insurance///////////////////////////////////////////////

	 @PostMapping("/addInsurance")
	 public void addInsurance(@RequestBody Insurance insurance) throws GeneralSecurityException, IOException, InterruptedException {
		 final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		 String range = "Insurance!A:G";
		 
		 Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
				 .setApplicationName(APPLICATION_NAME)
				 .build();
		 ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		 List<List<Object>> values = response.getValues();
		 
		 
		 values = Arrays.asList(
		         Arrays.asList(
		         		insurance.getName(), insurance.getEmail(), insurance.getContact(), insurance.getCity()
		         )
		 );
		 ValueRange body = new ValueRange().setValues(values);
		 service.spreadsheets().values().append(spreadsheetId, range, body)
							   .setValueInputOption("USER_ENTERED")
							   .setInsertDataOption("INSERT_ROWS")
							   .execute();
	 	
	  }





	 //////////////////////////////////////Insurance/////////////////////////////////////////////////
    
    public Beneficiary getBeneficiary() throws GeneralSecurityException, IOException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
    								.setApplicationName(APPLICATION_NAME)
    								.build();
    	
    	String range = "Beneficiary Database!A3:S";
    	ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
    	List<List<Object>> allBeneficiary = response.getValues();
    	
    	Beneficiary beneficiary = null;
  
    	List<BeneficiaryInfo> validBeneficiaries = new ArrayList<BeneficiaryInfo>();
    	int rowIndex=0;
		for(List<Object> list : allBeneficiary) {
			if(list.size()>18 && list.get(13).equals("TRUE") && list.get(16).equals("FALSE")) {
				validBeneficiaries.add(new BeneficiaryInfo(rowIndex+3, new Beneficiary(list.get(3).toString(),
																   					   list.get(4).toString(), 
																   					   list.get(5).toString(),
																   					   list.get(6).toString(),
																   					   list.get(7).toString(),
																   					   list.get(17).toString(),
																   					   list.get(18).toString()
																   						)));
			}
			rowIndex++;
		}
		if(validBeneficiaries.size()==0) {
			return null;
		}
    	beneficiary = validBeneficiaries.get(0).beneficiary;
		List<List<Object>> assigned = Arrays.asList(
    	        Arrays.asList(
    	                "TRUE"
    	        )
    	);
		
    	String updateRange = "Beneficiary Database!Q"+validBeneficiaries.get(0).row;
    	ValueRange body = new ValueRange().setValues(assigned);
		Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, updateRange, body);
		request.setValueInputOption("USER_ENTERED");
		request.execute();
    	
    	return beneficiary;
		
    }
}