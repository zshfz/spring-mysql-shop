package com.example.website.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers(HttpMethod.POST, "/comment/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/profile").authenticated()
                        .requestMatchers(HttpMethod.GET, "/write").authenticated()
                        .requestMatchers(HttpMethod.POST, "/write").authenticated()
                        .requestMatchers(HttpMethod.GET, "/edit/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/edit/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/delete/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/edit-profile/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/edit-profile/**").authenticated().
                        requestMatchers("/**").permitAll()
        );
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/", true)
        );
        http.logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/"));
        return http.build();
    }
}
