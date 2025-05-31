package com.example.website.repository;

import com.example.website.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM website.post WHERE MATCH(title) AGAINST(?1)", nativeQuery = true)
    List<Post> fullTextSearch(String searchText);
}
