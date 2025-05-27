package com.example.shop.controller;

import com.example.shop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(String filename) {
        String result = imageService.createPresignedUrl("test/" + filename);
        return result;
    }
}
