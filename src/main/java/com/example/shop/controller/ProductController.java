package com.example.shop.controller;

import com.example.shop.dto.ProductDto;
import com.example.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/write")
    public String showWriteForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "write";
    }

    @PostMapping("/write")
    public String saveProduct(@Valid ProductDto productDto, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "write";
        }
        productService.saveProduct(productDto, authentication);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.getProductList());
        return "product-list";
    }
}
