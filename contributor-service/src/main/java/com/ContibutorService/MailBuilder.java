package com.ContibutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ContibutorService.Models.Beneficiary;
import com.ContibutorService.Models.Donor;

@Service
public class MailBuilder {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private TemplateEngine templateEngine;
	 
    @Autowired
    public MailBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
 
    public String build(Beneficiary beneficiary, Donor donor) {
        Context context = new Context();
        if(beneficiary==null) {
        	context.setVariable("donor", donor);
        	return templateEngine.process("afterDelivery", context);
        }
        else {
        	context.setVariable("beneficiary", beneficiary);
            return templateEngine.process("mailTemplate", context);
        }
        
    }
    

	public void sendMail(String from, String to, Beneficiary beneficiary, Donor donor) {
    	MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject("Thank You");
            String content = build(beneficiary, donor);
            messageHelper.setText(content, true);
            
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

}
