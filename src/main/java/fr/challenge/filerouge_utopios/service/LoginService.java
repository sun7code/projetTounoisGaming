package fr.challenge.filerouge_utopios.service;

import fr.challenge.filerouge_utopios.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

// TODO: Use Spring Security for authentication if time permits it
@Service
public class LoginService {
    private final UserService userService;
    private final HttpSession httpSession;

    public LoginService(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    public boolean signup(User user) {
        if (!userService.existsByEmail(user.getEmail()) && !userService.existsByPseudo(user.getPseudo())) {
            userService.save(user);
            return true;
        }
        return false;
    }

    public boolean login(String email, String password) {
        if (!userService.existsByEmail(email)) return false;

        User user = userService.findByEmail(email);
        if (user.getPassword().equals(password)) {
            httpSession.setAttribute("isLoggedIn", Boolean.TRUE);
            httpSession.setAttribute("user", user);
            return true;
        }
        return false;
    }
    public User getUser(){
        return (User) httpSession.getAttribute("user");
    }
    public boolean isLoggedIn() {
        try {
            return (boolean) httpSession.getAttribute("isLoggedIn");
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public void logout() {
        httpSession.setAttribute("isLoggedIn", Boolean.FALSE);
        httpSession.removeAttribute("user");
    }
}
