package com.basic_shop.shop.comment;

import com.basic_shop.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComment(String content, Long parent, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUsername(customUser.getDisplayName());
        comment.setParentId(parent);
        commentRepository.save(comment);
    }
}
