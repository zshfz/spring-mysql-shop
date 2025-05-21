package com.basic_shop.shop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment")
    public String postComment(@RequestParam String content, @RequestParam Long parent, Authentication authentication) {
        commentService.saveComment(content, parent, authentication);
        return "redirect:/detail/" + parent;
    }
}
