package com.example.demo.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProductRequest {
    String name;
    String attributesJson;
}
