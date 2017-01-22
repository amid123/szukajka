/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import allegro.domain.dto.RegisterStatusDTO;
import allegro.domain.dto.UserRegistrationDTO;
import allegro.service.registration.ActivationProcess;
import allegro.service.registration.UserRegister;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("request")
@RequestMapping("/public")
public class RegistrationController {

    private static final Logger LOG = Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    private UserRegister register;
    @Autowired
    private ActivationProcess activationProces;

    @RequestMapping("/registration")
    public String showRegisterPage(Model model) throws InterruptedException {

        model.addAttribute("userRegistrationModel", new UserRegistrationDTO());
        return "/public/registration";
    }

    /**
     *
     *
     *
     * @ModelAttribute("employee") @Validated Employee employee, BindingResult
     * bindingResult, Model model
     *
     *
     * @param userRegistration
     * @param bindingResult
     * @return
     * @throws InterruptedException
     * @throws MessagingException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/register")
    public String doRegister(@ModelAttribute("userRegistrationModel") @Validated UserRegistrationDTO userRegistration, BindingResult bindingResult, Model model) throws InterruptedException, MessagingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        //ModelAndView model = new ModelAndView("/public/registerstatus");
        RegisterStatusDTO registerStatus = new RegisterStatusDTO();

        if (bindingResult.hasErrors()) {
            return "/public/registration";

        }

        register.register(userRegistration.getUsername(), userRegistration.getPassword(),
                userRegistration.getEmail(), "ROLE_USER");

        return "/public/registerstatus";
    }

    @RequestMapping("/activate")
    public ModelAndView activateAccount(HttpServletRequest request) throws InterruptedException {

        List<String> paramNames = Collections.list(request.getParameterNames());
        /**
         * Jeśli jest parametr Id to przkazujemy dalej
         */
        if (paramNames.contains("id")) {
            LOG.info(request.getParameter("id"));

            if (activationProces.finishActivationProcess(request.getParameter("id"))) {
                return new ModelAndView("/login");

            } else {
                return new ModelAndView("/public/activationerror");
            }
        }
        return new ModelAndView("/public/activationerror");
    }

}
