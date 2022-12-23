package com.example.demo.service;

import com.example.demo.mapper.MapStructMapper;
import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.ProductDto;
import com.example.demo.persistance.Product;
import com.example.demo.persistance.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    private final MapStructMapper mapStructMapper;

    public ProductDto getProduct(long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Unknown product id: " + id));
        return mapStructMapper.productToProductDto(product);
    }

    public ProductDto createProduct(CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .name(createProductRequest.getName())
                .attributesJson(createProductRequest.getAttributesJson())
                .build();
        Product savedProduct = productRepo.save(product);
        return mapStructMapper.productToProductDto(savedProduct);
    }
}
