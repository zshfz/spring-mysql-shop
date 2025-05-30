package com.example.website.controller;

import com.example.website.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(String filename) {
        return  s3Service.createPresignedUrl("test/" + filename);
    }
}
