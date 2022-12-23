package com.example.demo.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDto {
    long id;
    String name;
    String attributesJson;
}
