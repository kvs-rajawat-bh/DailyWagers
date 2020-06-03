package com.ContibutorService;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ContributorServiceApplication {

	public static void main(String[] args) throws BeansException, GeneralSecurityException, IOException {
		SpringApplication.run(ContributorServiceApplication.class, args);
	}

}
