package com.example.shop.service;

import com.example.shop.config.CustomUser;
import com.example.shop.dto.ProductDto;
import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import com.example.shop.repository.MemberRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public void saveProduct(ProductDto productDto, Authentication authentication) {
        Product product = new Product();
        product.setProductImageUrl(productDto.getProductImageUrl());
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long memberId = customUser.getId();

        Optional<Member> result = memberRepository.findById(memberId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("회원 정보 없음.");
        }
        Member member = result.get();
        product.setMember(member);
        productRepository.save(product);
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        Optional<Product> result = productRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        }
        return result.get();
    }
}
