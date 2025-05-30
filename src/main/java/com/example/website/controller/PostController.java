package com.example.website.controller;

import com.example.website.dto.PostRequest;
import com.example.website.service.PostService;
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

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "board";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWriteForm(Model model) {
        model.addAttribute("postRequest", new PostRequest());
        return "write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@Valid PostRequest postRequest, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "write";
        }
        postService.addPost(postRequest, authentication);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String showDetailForm(Model model, @PathVariable Long id) {
        model.addAttribute("post", postService.getPostDetail(id));
        return "detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        model.addAttribute("postRequest", postService.fillEditForm(id, authentication));
        model.addAttribute("postId", id);
        return "edit-write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String edit(@Valid PostRequest postRequest, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "edit-write";
        }
        postService.updatePost(id, postRequest);
        return "redirect:/post/" + id;
    }
}
