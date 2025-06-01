package com.example.website.controller;

import com.example.website.dto.EditProfileRequest;
import com.example.website.dto.PostRequest;
import com.example.website.service.MemberService;
import com.example.website.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        memberService.addMember(registerRequest);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(Authentication authentication, Model model) {
        model.addAttribute("memberInfo", memberService.getMember(authentication));
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit-profile/{id}")
    public String showEditProfileForm(@PathVariable Long id, Model model, Authentication authentication) {
        model.addAttribute("editProfileRequest", memberService.fillEditProfileRequest(id, authentication));
        model.addAttribute("memberId", id);
        return "edit-profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit-profile/{id}")
    public String editProfile(@Valid EditProfileRequest editProfileRequest, BindingResult bindingResult, @PathVariable Long id, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "edit-profile";
        }
        memberService.updateProfile(id, editProfileRequest, authentication);
        return "redirect:/profile";
    }
}
