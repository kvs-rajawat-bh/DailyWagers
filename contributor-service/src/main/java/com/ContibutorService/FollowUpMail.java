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
import org.springframework.web.bind.annotation.GetMapping;

import com.ContibutorService.Models.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

@CrossOrigin("*")
@Controller
public class FollowUpMail {
	
	@Autowired
	private UpdateSheet sheetUpdate;
	
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MailBuilder mailBuilder;
	
	//every day at 2 p.m
	
	@Scheduled(cron = "0 0 14 * * *")
	public void autoFollow() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
    								.setApplicationName(APPLICATION_NAME)
    								.build();
    	
    	String range = "Donor!A3:P";
    	ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
    	List<List<Object>> allDonors = response.getValues();
    	List<DonorInfo> validDonors = new ArrayList<DonorInfo>();
		int rowIndex=0;
		for(List<Object> list : allDonors) {
			if(list.size()>=16 && (list.get(13).toString().length()==0) 
							   && list.get(14).equals("FALSE") 
							   && (list.get(0).toString().length()>0)) 
			{
				
				validDonors.add(new DonorInfo(rowIndex+3, new Donor(list.get(5).toString(), list.get(6).toString(),
					list.get(7).toString(), list.get(8).toString())));
			}
			rowIndex++;
		}
		System.out.println(validDonors.size());
		
		if(validDonors.size()==0) {
			return;
		}
		List<List<Object>> values = Arrays.asList(
    	        Arrays.asList(
    	                "TRUE"
    	        )
    	);
		for(DonorInfo donor : validDonors) {
			mailBuilder.sendMail(env.getProperty("spring.mail.username"), donor.donor.getEmail(), null, donor.donor, true);
			range = "Donor!O"+donor.row;
			sheetUpdate.update(values, range);
		}
		
	}

}
