package com.basic_shop.shop.controller;

import com.basic_shop.shop.dto.ProductDto;
import com.basic_shop.shop.entity.Product;
import com.basic_shop.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    //글쓰기 화면 불러오기
    @GetMapping("/write")
    public String showWriteForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "write";
    }

    //글쓰기
    @PostMapping("/write")
    public String writePost(@Valid ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "write";
        }
        productService.addProduct(productDto);
        return "redirect:/";
    }

    //상세페이지 불러오기
    @GetMapping("/detail/{id}")
    public String showDetailPage(@PathVariable Long id, Model model) {
        Product product = productService.getDetail(id);
        model.addAttribute(product);
        return "detail";
    }
}
