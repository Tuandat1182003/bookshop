package vn.dazz.bookshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    @GetMapping("/my/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        // perform logout
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/home";
    }

}
