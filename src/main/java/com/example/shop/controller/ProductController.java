package com.example.shop.controller;

import com.example.shop.config.CustomUser;
import com.example.shop.dto.ProductDto;
import com.example.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWriteForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "write";
    }

    @PreAuthorize("isAuthenticated()")
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

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable Long id, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long memberId = customUser.getId();
        model.addAttribute("productDto", productService.getProductDto(id, memberId));
        return "edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String editProduct(@Valid ProductDto productDto, BindingResult bindingResult, Authentication authentication, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        productService.updateProduct(productDto, authentication, id);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/product/{id}")
    public String deleteItem(@PathVariable Long id, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        productService.deleteProduct(id, customUser.getId());
        return "redirect:/";
    }
}

