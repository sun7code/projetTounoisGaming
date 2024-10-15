package fr.challenge.filerouge_utopios.controller;

import fr.challenge.filerouge_utopios.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    private final LoginService loginService;

    public PageController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/profil")
    public String profil(Model model) {
        if (loginService.isLoggedIn() || loginService.getUser() != null) {
            return "profil";
        }
        return "redirect:/login";
    }
}
