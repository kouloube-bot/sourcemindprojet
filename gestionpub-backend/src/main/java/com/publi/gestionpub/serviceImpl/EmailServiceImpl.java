package com.publi.gestionpub.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.publi.gestionpub.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String to, String subject, String body) {
		
		 SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("camaramamata19@gmail.com");

        mailSender.send(message);
	}

}
