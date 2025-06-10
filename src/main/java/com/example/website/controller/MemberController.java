package com.example.website.controller;

import com.example.website.dto.EditProfileRequest;
import com.example.website.security.JwtUtil;
import com.example.website.service.MemberService;
import com.example.website.dto.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder; //JWT로 로그인 시켜주기 위해 DI

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

    //로그인 시켜주시고 로그인 성고하면 JWT 입장권 줘라
    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data, HttpServletResponse httpServletResponse) {
        //authenticate 인자로 아이디, 비번 넣어주면 DB에 있는 거랑 비교해서 로그인 시켜줌
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        //Authentication authentication에 로그인 된 유저 정보 넣어주는 코드 (수동으로 로그인 구현했기 때문에 직접 authentication에 넣어줘야 함)
        SecurityContextHolder.getContext().setAuthentication(auth);
        //SecurityContextHolder.getContext().getAuthentication(); 이렇게 하면 authentication 결과 남음

        //JWT 보내주는 코드
        String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        //JWT를 쿠키에 저장해주는 코드
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 10);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return jwt;
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
