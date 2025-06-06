package com.example.website.controller;

import com.example.website.dto.CommentRequest;
import com.example.website.dto.PostRequest;
import com.example.website.entity.Post;
import com.example.website.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        Page<Post> result = postService.pagination(1);
        model.addAttribute("posts", result);
        model.addAttribute("currentPage", result.getNumber() + 1);
        model.addAttribute("totalPages", result.getTotalPages());
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
        model.addAttribute("commentRequest", new CommentRequest());
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Authentication authentication) {
        postService.deletePost(id, authentication);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(String searchText, Model model) {
        model.addAttribute("posts", postService.fullTextSearch(searchText));
        return "board";
    }

    @GetMapping("/board/page/{id}")
    public String pagination(Model model, @PathVariable Integer id) {
        Page<Post> result = postService.pagination(id);
        model.addAttribute("posts", result);
        model.addAttribute("currentPage", result.getNumber() + 1);
        model.addAttribute("totalPages", result.getTotalPages());
        return "board";
    }
}
