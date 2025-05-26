package com.basic_shop.shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    @NotBlank(message = "Please enter the product name.")
    private String title;
    @NotNull(message = "Please enter the price.")
    @Min(value = 0, message = "The price must be at least 0.")
    private Integer price;
    @NotBlank(message = "Please enter the description.")
    @Size(max = 1000, message = "The description must be under 1000 characters.")
    private String description;
    private String createdBy;
}
