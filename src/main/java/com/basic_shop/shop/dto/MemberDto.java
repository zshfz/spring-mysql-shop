package com.basic_shop.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
    private Long id;
    @NotBlank(message = "Please enter the displayName.")
    private String displayName;
    @NotBlank(message = "Please enter the email.")
    private String username;
    @NotBlank(message = "Please enter the password")
    private String password;
}
