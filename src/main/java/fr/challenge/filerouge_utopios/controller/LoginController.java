
package fr.challenge.filerouge_utopios.controller;

import fr.challenge.filerouge_utopios.entity.User;
import fr.challenge.filerouge_utopios.service.CountryService;
import fr.challenge.filerouge_utopios.service.LoginService;
import fr.challenge.filerouge_utopios.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final LoginService service;
    private final CountryService countryService;
    private final UserService userService;

    public LoginController(LoginService service, CountryService countryService, UserService userService) {
        this.service = service;
        this.countryService = countryService;
        this.userService = userService;
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("countries", countryService.findAll());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        System.out.println(user.getBirthDate());
        if (!result.hasErrors()) {
            if (!service.signup(user)) {
                if (userService.existsByEmail(user.getEmail())) {
                    result.rejectValue("email", "error.user", "Email already exists");
                }
                if (userService.existsByPseudo(user.getPseudo())) {
                    result.rejectValue("pseudo", "error.user", "Pseudo already exists");
                }
            } else {
                service.login(user.getEmail(), user.getPassword());
                return "redirect:/";
            }
        }

        model.addAttribute("countries", countryService.findAll());
        return "signup";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        if (service.login(email, password)) {
            return "redirect:/";
        }

        model.addAttribute("error", "Email or password is incorrect");
        return "login";

    }

    @RequestMapping("/logout")
    public String logout() {
        service.logout();
        return "redirect:/";
//        throw new ResponseStatusException(HttpStatusCode.valueOf(404));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPOST() {
        service.logout();
        return "redirect:/";
    }

    @RequestMapping("/newPassword")
    public String newPassword(Model model) {
        model.addAttribute("errorEmail", null);
        model.addAttribute("errorPassword", null);
        return "updateProfil";
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
    public String updatePasswordPOST(@RequestParam("email") String email,
                                 @RequestParam("login-password") String password,
                                 @RequestParam("login-password-confirm") String passwordConfirm,
                                 Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("errorEmail", "Invalid email");
            if (!password.equals(passwordConfirm)) {
                model.addAttribute("errorPassword", "Passwords do not match");
            }else {
                model.addAttribute("errorPassword", null);
            }
            return "/updateProfil";
        }
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("errorPassword", "Passwords do not match");
            return "/updateProfil";
        }else {
            user.setPassword(password);
            userService.save(user);
            model.addAttribute("passwordUpdate", "Passwords updated successfully");
            return "/updateProfil";
        }

    }
}
