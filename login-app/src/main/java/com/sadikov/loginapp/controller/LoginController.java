package com.sadikov.loginapp.controller;

import com.sadikov.loginapp.dto.UserCreateDTO;
import com.sadikov.loginapp.exceptions.UserNotCreatedException;
import com.sadikov.loginapp.exceptions.UserNotFoundException;
import com.sadikov.loginapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public String index() {
        return "auth.html";
    }

    @GetMapping("/create")
    public String createUser(@RequestHeader HttpHeaders header) {
        return "registration.html";
    }

    @PostMapping("/create/new")
    public String createUser(@ModelAttribute UserCreateDTO userCreateDTO) {
        try {
            userService.createNewUser(userCreateDTO);
        } catch (UserNotFoundException | UserNotCreatedException e) {
            throw new RuntimeException(e);
        }
        return "infoReg.html";
    }
}
