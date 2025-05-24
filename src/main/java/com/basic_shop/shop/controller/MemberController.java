package com.basic_shop.shop.controller;

import com.basic_shop.shop.dto.MemberDto;
import com.basic_shop.shop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //로그인 폼 불러오기
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    //회원가입 폼 불러오기
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "register";
    }

    //회원가입
    @PostMapping("/member")
    public String addMember(@Valid MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        memberService.addMember(memberDto);
        return "redirect:/";
    }
}
