/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.email;

import javax.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author arek
 */
public interface EmailHelper {

    public JavaMailSenderImpl init() throws MessagingException;

    public void sendEmail(String to, String subject, String body) throws MessagingException;

}
