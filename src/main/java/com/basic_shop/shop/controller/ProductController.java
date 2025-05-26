package com.basic_shop.shop.controller;

import com.basic_shop.shop.dto.ProductDto;
import com.basic_shop.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //메인화면 불러오기 (상품 목록)
    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("products", productService.getProductList());
        return "main";
    }

    //상품등록 화면 불러오기
    @GetMapping("/write")
    public String showWriteForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "write";
    }

    //상품 등록하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writePost(@Valid ProductDto productDto, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "write";
        }
        productService.addProduct(productDto, authentication);
        return "redirect:/";
    }

    //상세페이지 불러오기
    @GetMapping("/detail/{id}")
    public String showDetailPage(@PathVariable Long id, Model model) {
        model.addAttribute("productDto", productService.getProductDto(id));
        return "detail";
    }

    //상품 수정 화면 불러오기
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("productDto", productService.getProductDto(id));
        return "edit";
    }

    //상품 수정하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @Valid ProductDto productDto, BindingResult bindingResult, Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        productService.updatePost(id, productDto, authentication);
        return "redirect:/detail/" + id;
    }

    //상품 삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Authentication authentication) {
        productService.deletePost(id, authentication);
        return "redirect:/";
    }
}
