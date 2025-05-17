package com.basic_shop.shop.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("name", "이정민");
        return "list.html";
    }
}
