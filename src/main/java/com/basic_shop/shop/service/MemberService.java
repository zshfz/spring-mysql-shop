package com.basic_shop.shop.service;

import com.basic_shop.shop.dto.MemberDto;
import com.basic_shop.shop.dto.ProductDto;
import com.basic_shop.shop.entity.Member;
import com.basic_shop.shop.entity.Product;
import com.basic_shop.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void addMember(MemberDto memberDto) {
        Optional<Member> result = memberRepository.findByUsername(memberDto.getUsername());
        if (result.isPresent()) {
            throw new IllegalArgumentException("This username is already taken.");
        }

        Member member = new Member();
        member.setDisplayName(memberDto.getDisplayName());
        member.setUsername(memberDto.getUsername());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(member);
    }

}
