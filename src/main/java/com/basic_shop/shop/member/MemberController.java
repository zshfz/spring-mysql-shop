package com.basic_shop.shop.member;

import com.basic_shop.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @PostMapping("/register")
    public String addMember(@RequestParam String displayName, @RequestParam String username, @RequestParam String password) {
        memberService.saveMember(displayName, username, password);
        return "login.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return "mypage.html";
        } else {
            return "login.html";
        }
    }


}
