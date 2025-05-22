package com.basic_shop.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }

    @GetMapping("/product")
    public String showProductList() {
        return "product-list";
    }
}
