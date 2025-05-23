package com.basic_shop.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    //로그인 폼 불러오기
    @GetMapping("/login")
    public String showLoginForm() {
        return "login.html";
    }

    //회원가입 폼 불러오기
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register.html";
    }
}
