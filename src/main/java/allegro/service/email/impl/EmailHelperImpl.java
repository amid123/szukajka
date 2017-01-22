/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.email.impl;

import allegro.service.email.EmailHelper;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

/**
 *
 * w ustawieniach poczty musi być
 */
@Service
@Scope("request")
public class EmailHelperImpl implements EmailHelper {

    
    private String host;
    private String port;
    private String userName;
    private String password;
    private String defaultEncoding;
    private String protocol;
    
    
    public JavaMailSenderImpl init() throws MessagingException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("amid123@gmail.com");
        mailSender.setPassword("ae3vebxnmsci");

        mailSender.setDefaultEncoding("ISO-8859-2");

        mailSender.setProtocol("smtp");

        
        Properties properties = new Properties();

        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {

        JavaMailSenderImpl mailSender = this.init();

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        mailSender.send(message);

    }
}
