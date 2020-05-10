package com.ContibutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ContibutorService.models.Beneficiary;

@Service
public class MailBuilder {
	
	private TemplateEngine templateEngine;
	 
    @Autowired
    public MailBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
 
    public String build(Beneficiary beneficiary) {
    	System.out.println(beneficiary.getName());
        Context context = new Context();
        context.setVariable("beneficiary", beneficiary);
        return templateEngine.process("mailTemplate", context);
    }

}
