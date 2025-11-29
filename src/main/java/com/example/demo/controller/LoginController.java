package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/testPage")
    public String testPage() {
        return "pages/testPage";
    }


    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        if (userService.checkLogin(username, password)) {
            return "redirect:/testPage";
        } else {
            return "redirect:/login";
        }
    }
}
