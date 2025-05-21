package com.basic_shop.shop;

import com.basic_shop.shop.member.Member;
import com.basic_shop.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUsername(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 아이디 입니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("zshfz8634".equals(username)){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        CustomUser customUser = new CustomUser(result.get().getUsername(), result.get().getPassword(), authorities);
        customUser.setDisplayName(result.get().getDisplayName());
        customUser.setId(result.get().getId());
        return customUser;
    }
}


