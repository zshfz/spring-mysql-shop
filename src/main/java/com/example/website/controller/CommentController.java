package com.example.website.controller;

import com.example.website.dto.CommentRequest;
import com.example.website.service.CommentService;
import com.example.website.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable Long id, @Valid CommentRequest commentRequest, BindingResult bindingResult, Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postService.getPostDetail(id));
            return "detail";
        }
        commentService.addComment(id, authentication, commentRequest);
        return "redirect:/post/" + id;
    }
}
