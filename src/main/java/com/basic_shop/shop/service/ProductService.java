package com.basic_shop.shop.service;

import com.basic_shop.shop.dto.ProductDto;
import com.basic_shop.shop.entity.Product;
import com.basic_shop.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //상품 목록 불러오기
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
}
