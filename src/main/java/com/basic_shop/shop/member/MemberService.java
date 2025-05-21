package com.basic_shop.shop.member;

import com.basic_shop.shop.CustomUser;
import com.basic_shop.shop.orders.Orders;
import com.basic_shop.shop.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrdersRepository ordersRepository;

    public void saveMember(String displayName, String username, String password) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수입니다.");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("아이디는 필수입니다.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        Member member = new Member();
        member.setDisplayName(displayName);
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    //dto 사용 예제
    public MemberDto getUser(Long id) {
        Optional<Member> result = memberRepository.findById(id);
        MemberDto memberDto = new MemberDto(result.get().getUsername(), result.get().getDisplayName());
        return memberDto;
    }

    public List<Orders> findPersonalOrders(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return ordersRepository.findAllByMemberId(customUser.getId());
    }
}
