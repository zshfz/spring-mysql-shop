package com.example.website.advice;

import com.example.website.entity.Member;
import com.example.website.repository.MemberRepository;
import com.example.website.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final MemberRepository memberRepository;

    @ModelAttribute("memberInfo")
    public Member addLoggedInMember(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        CustomUser principal = (CustomUser) authentication.getPrincipal();

        Optional<Member> result = memberRepository.findByUsername(principal.getUsername());
        if (result.isEmpty()) {
            return null;
        }
        return result.get();
    }
}
