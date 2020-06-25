package com.ContibutorService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ContibutorService.Models.Beneficiary;
import com.ContibutorService.Models.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

@CrossOrigin("*")
@Controller
public class AutoDonorAssigner {

	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";

	@Autowired
	private MailBuilder mailBuilder;

	@Autowired
	private Environment env;

	@Scheduled(fixedRate = 3600000)
	public void syncDonorBeneficiary() throws GeneralSecurityException, IOException, InterruptedException {

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		String range = "Donor!A3:N";

		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();

		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> allDonors = response.getValues();

		List<DonorInfo> validDonors = new ArrayList<DonorInfo>();
		int rowIndex=0;
		for(List<Object> list : allDonors) {
			if(list.get(0).equals("") && list.get(1).equals("")	&& list.get(2).equals("")) {
				validDonors.add(new DonorInfo(rowIndex+3, new Donor(list.get(5).toString(), list.get(6).toString(),
					list.get(7).toString(), list.get(8).toString())));
			}
			rowIndex++;
		}
		
		range = "Beneficiary Database!A3:Q";
		response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> allBeneficiary = response.getValues();
		
		
		List<BeneficiaryInfo> validBeneficiaries = new ArrayList<BeneficiaryInfo>();
		rowIndex=0;
		for(List<Object> list : allBeneficiary) {
			if(list.size()>18 && list.get(13).equals("TRUE") && list.get(16).equals("FALSE")) {
				validBeneficiaries.add(new BeneficiaryInfo(rowIndex+3, new Beneficiary(list.get(3).toString(),
																   list.get(4).toString(), 
																   list.get(5).toString(), list.get(6).toString(),
																   list.get(7).toString(),
																   list.get(17).toString(),
											   					   list.get(18).toString())));
			}
			rowIndex++;
		}
	
		if(validDonors.size()==0 || validBeneficiaries.size()==0) {
			return;
		}
		
		int i = 0;
	
		while(i < Math.min(validBeneficiaries.size(), validDonors.size())) {
			
			//firstly mark beneficiary as assigned
			List<List<Object>> values = Arrays.asList(Arrays.asList("TRUE"));
			String updateRange = "Beneficiary Database!Q" + validBeneficiaries.get(i).row;
			ValueRange body = new ValueRange().setValues(values);
			Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, updateRange,
					body);
			request.setValueInputOption("USER_ENTERED");
			request.execute();
			
			values = Arrays.asList(
	    	        Arrays.asList(
	    	        		validBeneficiaries.get(i).beneficiary.getName(),
	    	        		validBeneficiaries.get(i).beneficiary.getContact(),
	    	        		validBeneficiaries.get(i).beneficiary.getAddress()
	    	        )
	    	);
			
			updateRange = "Donor!A"+validDonors.get(i).row+":C";
			
			body = new ValueRange().setValues(values);
			request = service.spreadsheets().values().update(spreadsheetId, updateRange, body);
			request.setValueInputOption("USER_ENTERED");
			request.execute();
			
			//high risk code area for test
			mailBuilder.sendMail(env.getProperty("spring.mail.username"), validDonors.get(i).donor.getEmail(), 
																		  validBeneficiaries.get(i).beneficiary, validDonors.get(i).donor, false);
			i++;
			
		}
	}
}

class DonorInfo {
	int row;
	Donor donor;

	DonorInfo(int row, Donor donor) {
		this.row = row;
		this.donor = donor;
	}
}

class BeneficiaryInfo{
	int row;
	Beneficiary beneficiary;
	
	public BeneficiaryInfo(int row, Beneficiary beneficiary) {
		this.row = row;
		this.beneficiary = beneficiary;
	}
	
}
