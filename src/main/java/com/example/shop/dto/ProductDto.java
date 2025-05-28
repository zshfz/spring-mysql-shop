package com.example.shop.dto;

import com.example.shop.entity.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    @NotBlank(message = "상품 이미지를 선택하세요.")
    private String productImageUrl;
    @NotBlank(message = "상품 이름을 입력하세요.")
    private String title;
    @NotNull(message = "가격을 입력하세요.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;
    @NotBlank(message = "상품 설명을 입력하세요.")
    private String description;
    private Member member;
    private LocalDateTime createdAt;
}
