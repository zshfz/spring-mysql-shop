package com.basic_shop.shop.service;

import com.basic_shop.shop.dto.ProductDto;
import com.basic_shop.shop.entity.Product;
import com.basic_shop.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //상품 목록 불러오기 (단순 조회용이라 DTO 사용 X)
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    //상품 등록
    public void addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }

    //상품 찾기
    public Product getProduct(Long id) {
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }else {
            throw new IllegalArgumentException("The requested product does not exist.");
        }
    }

    //상품 찾아서 DTO로 변환
    public ProductDto getProductDto(Long id) {
        Product product = getProduct(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    //상품 수정
    public void updatePost(Long id, ProductDto productDto) {
        Product product = new Product();
        product.setId(id);
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }
}
