package com.example.website.service;

import com.example.website.dto.RegisterRequest;
import com.example.website.entity.Member;
import com.example.website.repository.MemberRepository;
import com.example.website.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void addMember(RegisterRequest registerRequest) {
        Optional<Member> result = memberRepository.findByUsername(registerRequest.getUsername());
        if (result.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다");
        }
        Member member = new Member();

        if (registerRequest.getProfileImageUrl() == null || registerRequest.getProfileImageUrl().isBlank()) {
            member.setProfileImageUrl("https://zshfz8634springpractice.s3.ap-northeast-2.amazonaws.com/test/default-image.png");
        }else{
            member.setProfileImageUrl(registerRequest.getProfileImageUrl());
        }

        member.setDisplayName(registerRequest.getDisplayName());
        member.setUsername(registerRequest.getUsername());
        member.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        memberRepository.save(member);
    }

    public Member getMember(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Optional<Member> result = memberRepository.findByUsername(customUser.getUsername());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        }
        return result.get();
    }
}
