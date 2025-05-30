package com.example.website.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100)
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 1000)
    private String content;
    private String postImageUrl;
}
