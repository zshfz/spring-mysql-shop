package com.example.website.service;

import com.example.website.dto.PostRequest;
import com.example.website.entity.Member;
import com.example.website.entity.Post;
import com.example.website.repository.MemberRepository;
import com.example.website.repository.PostRepository;
import com.example.website.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void addPost(PostRequest postRequest, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        Optional<Member> result = memberRepository.findByUsername(customUser.getUsername());
        if (result.isEmpty()) {
            throw new IllegalStateException("작성자 정보를 찾을 수 없습니다.");
        }
        Member member = result.get();

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setPostImageUrl(postRequest.getPostImageUrl());
        post.setMember(member);
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostDetail(Long id) {
        Optional<Post> result = postRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글이 없습니다.");
        }
        return result.get();
    }

    public PostRequest fillEditForm(Long id, Authentication authentication) {
        Post post = getPostDetail(id);

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        if (!post.getMember().getUsername().equals(customUser.getUsername())) {
            throw new AccessDeniedException("본인의 글만 수정할 수 있습니다.");
        }

        PostRequest postRequest = new PostRequest();
        postRequest.setTitle(post.getTitle());
        postRequest.setContent(post.getContent());
        postRequest.setPostImageUrl(post.getPostImageUrl());
        return postRequest;
    }

    public void updatePost(Long id, PostRequest postRequest) {
        Post post = getPostDetail(id);

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setPostImageUrl(postRequest.getPostImageUrl());
        postRepository.save(post);
    }

    public void deletePost(Long id, Authentication authentication) {
        Post post = getPostDetail(id);

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        if (!post.getMember().getUsername().equals(customUser.getUsername())) {
            throw new AccessDeniedException("본인의 글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    public List<Post> fullTextSearch(String searchText) {
        return postRepository.fullTextSearch(searchText);
    }

    public Page<Post> pagination(Integer id) {
       return postRepository.findPageBy(PageRequest.of(id - 1, 7));
    }
}
