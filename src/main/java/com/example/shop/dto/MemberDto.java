package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "password")
public class MemberDto {
    private Long id;
    private String profileImageUrl;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String displayName;
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하로 입력해주세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 3, message = "비밀번호는 최소 3자 이상이어야 합니다.")
    private String password;
}
