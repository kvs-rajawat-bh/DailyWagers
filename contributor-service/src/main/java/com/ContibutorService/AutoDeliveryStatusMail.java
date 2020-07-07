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

import com.ContibutorService.Models.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

@CrossOrigin("*")
@Controller
public class AutoDeliveryStatusMail {
	
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String spreadsheetId = "1sR8mzuk0f9uSf8GL4AU7niYiDSayDsvF4yVSR98AYF8";
	
	@Autowired
    private MailBuilder mailBuilder;
	
	@Autowired
	private UpdateSheet sheetUpdate;
	
	@Autowired
	private Environment env;
	
	@Scheduled(cron="0 0 * * * *")
	public void autoDeliveryMail() throws GeneralSecurityException, IOException {
		System.out.println("Auto Delivery mail starting");
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
			if(list.size()>=16 && (list.get(13).equals("Delivered") || list.get(13).equals("delivered")) && list.get(15).equals("FALSE")) {
				validDonors.add(new DonorInfo(rowIndex+3, new Donor(list.get(5).toString(), list.get(6).toString(),
					list.get(7).toString(), list.get(8).toString())));
			}
			rowIndex++;
		}
		
		if(validDonors.size()==0) {
			return;
		}
		List<List<Object>> values = Arrays.asList(
    	        Arrays.asList(
    	                "TRUE"
    	        )
    	);
		for(DonorInfo donor : validDonors) {
			try{
				mailBuilder.sendMail(env.getProperty("spring.mail.username"), donor.donor.getEmail(), null, donor.donor, false);
				Thread.sleep(10000);
				range = "Donor!P"+donor.row;
				sheetUpdate.update(values, range);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			
		}
	}
}
