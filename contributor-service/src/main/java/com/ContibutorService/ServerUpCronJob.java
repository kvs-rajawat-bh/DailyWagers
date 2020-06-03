package com.ContibutorService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ServerUpCronJob {
	@Scheduled(cron = "0 * * * * *")
	public void upServer() throws IOException{
		try {
			System.out.println("calling");
			URL url = new URL("https://daily-wagers.herokuapp.com/");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}

}
