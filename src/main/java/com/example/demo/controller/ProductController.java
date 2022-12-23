package com.example.demo.controller;

import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.ProductDto;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/{id}")
    public ProductDto getOrder(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ProductDto createOrder(@RequestBody CreateProductRequest createProductRequest) {
        return productService.createOrder(createProductRequest);
    }
}
