package com.example.shop.service;

import com.example.shop.dto.MemberDto;
import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void addMember(MemberDto memberDto) {
        if (memberRepository.findByUsername(memberDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (memberRepository.findByDisplayName(memberDto.getDisplayName()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        Member member = new Member();
        member.setDisplayName(memberDto.getDisplayName());
        member.setUsername(memberDto.getUsername());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setProfileImageUrl(memberDto.getProfileImageUrl());
        memberRepository.save(member);
    }
}
