package com.example.shop.service;

import com.example.shop.config.CustomUser;
import com.example.shop.dto.ProductDto;
import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import com.example.shop.repository.MemberRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    public void updateProduct(ProductDto productDto, Authentication authentication, Long id) {
        Product product = getProduct(id);
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long memberId = customUser.getId();
        checkProductOwner(product, memberId);
        product.setId(id);
        product.setProductImageUrl(productDto.getProductImageUrl());
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());

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

    public List<Product> findProductByMemberId(Long memberId) {
        return productRepository.findByMemberId(memberId);
    }

    public ProductDto getProductDto(Long id, Long memberId) {
        Product product = getProduct(id);
        checkProductOwner(product, memberId);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setProductImageUrl(product.getProductImageUrl());
        productDto.setMember(product.getMember());
        return productDto;
    }

    public void checkProductOwner(Product product, Long memberId) {
        if (product.getMember() == null || !product.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
    }

    public void deleteProduct(Long id, Long memberId) {
        Product product = getProduct(id);
        checkProductOwner(product, memberId);
        productRepository.deleteById(id);
    }
}
