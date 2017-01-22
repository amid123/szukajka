/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import allegro.domain.dto.ContactFormDTO;
import allegro.service.email.EmailHelper;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Controller
@Scope("request")
@RequestMapping("/public")
public class PublicPagesController {

    @Autowired
    EmailHelper email;

    @RequestMapping("/index")
    public ModelAndView showIndexPage() throws InterruptedException {
        ModelAndView model = new ModelAndView("/public/index");

        return model;
    }

    @RequestMapping("/about")
    public ModelAndView showAboutPage() throws InterruptedException {
        ModelAndView model = new ModelAndView("/public/about");

        return model;
    }

    @RequestMapping("/privacypolicy")
    public ModelAndView showPrivacyPolicyPage() throws InterruptedException {
        ModelAndView model = new ModelAndView("/public/privacy_policy");

        return model;
    }

    @RequestMapping("/contact")
    public ModelAndView showContactPage() throws InterruptedException {
        ModelAndView model = new ModelAndView("/public/contact");
        model.addObject("contactDto", new ContactFormDTO());
        return model;
    }

    @RequestMapping("/contactpost")
    public ModelAndView postContactForm(ContactFormDTO form) throws InterruptedException, MessagingException, UnsupportedEncodingException {
        ModelAndView model = new ModelAndView("/public/index");
        model.addObject("contactDto", new ContactFormDTO());

        this.email.sendEmail("amid123@o2.pl", "Wiadomość z formulaza kontaktowego", "Wiadomość od: " + form.getName()
                + "Email: " + form.getFrom()
                + " Treść: " + form.getMessage());// + form.+form.getMessage());

        return model;
    }

    @RequestMapping("/404")
    public ModelAndView show404() {
        ModelAndView model = new ModelAndView("/public/404");

        return model;
    }

    @RequestMapping("/reg")
    public ModelAndView showRegulamin() {
        ModelAndView model = new ModelAndView("/public/regulamin");

        return model;
    }

}
