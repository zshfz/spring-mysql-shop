package com.example.website.service;

import com.example.website.dto.CommentRequest;
import com.example.website.entity.Comment;
import com.example.website.entity.Member;
import com.example.website.entity.Post;
import com.example.website.repository.CommentRepository;
import com.example.website.repository.MemberRepository;
import com.example.website.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public void addComment(Long id, Authentication authentication, CommentRequest commentRequest) {
        Post post = postService.getPostDetail(id);

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Optional<Member> result = memberRepository.findByUsername(customUser.getUsername());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("작성자 정보를 찾을 수 없습니다.");
        }
        Member member = result.get();

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setMember(member);
        comment.setPost(post);
        commentRepository.save(comment);
    }
}
