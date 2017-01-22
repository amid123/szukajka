/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import java.io.Serializable;
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
public class LoginController implements Serializable {

    @RequestMapping("/login")
    public ModelAndView _login() throws InterruptedException {
        ModelAndView model = new ModelAndView("/login");

        return model;
    }

    @RequestMapping("/403")
    public ModelAndView _403() throws InterruptedException {
        ModelAndView model = new ModelAndView("/403");

        return model;
    }

    @RequestMapping("/")
    public String _homepage() {
        return "redirect:/public/index";
    }
}
