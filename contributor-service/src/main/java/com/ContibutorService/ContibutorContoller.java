package com.ContibutorService;

import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ContibutorService.Models.Beneficiary;
import com.ContibutorService.Models.Donor;





@CrossOrigin("*")
@RestController
public class ContibutorContoller {
	
	@Autowired
	private BeneficiaryRepository repo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private DonorRepository donorRepo;
	
	@GetMapping("/getBeneficiary")
	public String get(Donor donor) throws MessagingException {
		System.out.println("called");
		
		List<Beneficiary> list = repo.findByVerifiedAndAddressConfirmed(true, true);
		Collections.shuffle(list);
		return sendMail("kirtivardhan80@gmail.com", list.get(0));
	}
	
	
	public String sendMail(String to, Beneficiary beneficiary) throws MessagingException
	{
		MimeMessage msg  = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(to);

        helper.setSubject("Beneficiary Information");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<!DOCTYPE html>\r\n" + 
        		"<html>\r\n" + 
        		"<body>\r\n" + 
        		"\r\n" + 
        		"<p>"+beneficiary.getName()+" "+beneficiary.getPostalAddress()+"</p>\r\n" + 
        		"\r\n" + 
        		"<button onclick=\"myFunction()\">Try it</button>\r\n" + 
        		"\r\n" + 
        		"<p id=\"demo\"></p>\r\n" + 
        		"\r\n" + 
        		"<script>\r\n" + 
        		"function myFunction() {\r\n" + 
        		"  var greeting;\r\n" + 
        		"  var time = new Date().getHours();\r\n" + 
        		"  if (time < 10) {\r\n" + 
        		"    greeting = \"Good morning\";\r\n" + 
        		"  } else if (time < 20) {\r\n" + 
        		"    greeting = \"Good day\";\r\n" + 
        		"  } else {\r\n" + 
        		"    greeting = \"Good evening\";\r\n" + 
        		"  }\r\n" + 
        		"  document.getElementById(\"demo\").innerHTML = greeting;\r\n" + 
        		"}\r\n" + 
        		"</script>\r\n" + 
        		"\r\n" + 
        		"</body>\r\n" + 
        		"</html>", true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
        return "email sent";

	}
	
	@GetMapping("/addDonor")
	public String register(Donor donor) {
		
		try {
			donorRepo.save(donor);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return "Successfully Registered";
		
		
	}


}
