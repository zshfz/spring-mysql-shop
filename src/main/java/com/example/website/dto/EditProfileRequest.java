package com.example.website.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProfileRequest {
    private String profileImageUrl;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String displayName;
}
