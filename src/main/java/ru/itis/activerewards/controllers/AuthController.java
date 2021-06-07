package ru.itis.activerewards.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.activerewards.forms.NewUserForm;
import ru.itis.activerewards.services.UserService;
import ru.itis.activerewards.services.AuthenticationService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String loginPage() {
        return "login-page";
    }

    @GetMapping("/register")
    public String getSignUpPage() {
        return "sign-up";
    }

    @PostMapping("/register")
    public void registerUser(NewUserForm form) {
        userService.registerUser(form);
    }
}
