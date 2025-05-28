package com.example.shop.controller;

import com.example.shop.config.CustomUser;
import com.example.shop.dto.MemberDto;
import com.example.shop.service.MemberService;
import com.example.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "register";
    }

    @PostMapping("/register")
    public String addMember(@Valid MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        memberService.addMember(memberDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long memberId = customUser.getId();
        model.addAttribute("products", productService.findPostByMemberId(memberId));
        return "profile";
    }
}
