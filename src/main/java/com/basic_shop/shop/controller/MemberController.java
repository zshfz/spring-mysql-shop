package com.basic_shop.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login.html";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register.html";
    }
}
