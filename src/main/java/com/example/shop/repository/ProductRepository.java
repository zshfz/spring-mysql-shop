package com.example.shop.repository;


import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByMemberId(Long memberId);
}
