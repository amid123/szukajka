/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import allegro.domain.dto.UserSettingsDTO;
import allegro.service.UserSettingsService;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Controller
@Scope("request")
public class UserSettingsController {

    private static final Logger LOG = Logger.getLogger(UserSettingsController.class.getName());

    @Autowired
    UserSettingsService userSettingsSetvice;
    
    @Autowired
    Authentication authentication;

    @RequestMapping("/settings")
    public ModelAndView showUserSettings(HttpServletRequest request) throws InterruptedException {
        List<String> paramNames = Collections.list(request.getParameterNames());
        ModelAndView model = new ModelAndView("/settings");

        try {

           // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String logedUserName = authentication.getName(); 
            model.addObject("logedUser", logedUserName);

            model.addObject("settingsDto", userSettingsSetvice.getUserSettings(logedUserName));

        } catch (NullPointerException ex) {
            LOG.info("Settings: Nie znaleziono urzytkownika NPE errot");
            model.addObject("logedUser", "Marian Kopytko");

            model.addObject("settingsDto", new UserSettingsDTO());
        }

        return model;
    }

    @RequestMapping("/updatesettings")
    public ModelAndView updateSettings(HttpServletRequest request, UserSettingsDTO settingsDto) throws InterruptedException {
        List<String> paramNames = Collections.list(request.getParameterNames());
        ModelAndView model = new ModelAndView("/settings");

        String logedUserName = new String();
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logedUserName = auth.getName(); //get logged in username
            model.addObject("logedUser", logedUserName);

            LOG.info("Nazwa uzytkownika: " + auth.getName());

        } catch (NullPointerException ex) {
            LOG.info("Settings: Nie znaleziono urzytkownika NPE errot");
            model.addObject("logedUser", "Marian Kopytko");
        }
        this.userSettingsSetvice.updateUserSettings(logedUserName, settingsDto);
        model.addObject("settingsDto", userSettingsSetvice.getUserSettings(logedUserName));
        return model;
    }
}
