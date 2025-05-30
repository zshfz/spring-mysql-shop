package com.example.website.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/")
    public String showMainPage() {
        return "board";
    }
}
