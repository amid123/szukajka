/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.registration;

import allegro.domain.User;
import allegro.domain.VerificationToken;
import allegro.service.email.EmailHelper;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import allegro.domain.repositories.UserRepository;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("request")
public class ActivationProcess {

    @Autowired
    UserRepository userDao;

    @Autowired
    EmailHelper mailer;
    private static final Logger LOG = Logger.getLogger(ActivationProcess.class.getName());

    private String generateActivationToken(String username) {
        return UUID.randomUUID().toString();
    }

    private void sendActivationMessage(String token, String email) throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {
        String message = "Twoje konto nie jest jeszcze aktywne, aby aktywowć konto kliknij na link poniżej: \n";
        String link = "http://its3g.pl:8080/allegro/public/activate?id=" + token;
        mailer.sendEmail(email, "Link aktywacyjny - Kupujemy od jednego", message + link);
    }

    public void startActivationProcess(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {

        if (!user.getEmail().isEmpty() && user.getEmail() != null) {
            String string_token = this.generateActivationToken(user.getUsername());
            VerificationToken token = new VerificationToken(string_token, user);
            userDao.addNewVerificationToken(token);
            this.sendActivationMessage(string_token, user.getEmail());
        }
    }

    public boolean finishActivationProcess(String tokenString) {
        VerificationToken token = userDao.findToken(tokenString);

        /**
         * Gdy czas jeszcze nie upłyną to aktywujemy konto
         */
        if (token.getExpiryDate().after(Calendar.getInstance().getTime())) {
            userDao.enableUserAccount(token.getUser().getUsername());
            return true;
        } else {
            return false;
        }
    }
}
