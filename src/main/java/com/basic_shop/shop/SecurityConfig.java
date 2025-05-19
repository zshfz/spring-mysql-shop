package com.basic_shop.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  //이거 없으면 @PreAuthorize 무시됨
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable()); //개발중에 잠깐 꺼두기
        httpSecurity.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );
        httpSecurity.formLogin((formLogin)
                -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/")
        );
        httpSecurity.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 경로
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제 (선택)
        );
        httpSecurity.exceptionHandling(e -> e
                .accessDeniedPage("/forbidden")
        );
        return httpSecurity.build();
    }
}
