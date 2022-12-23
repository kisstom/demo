package com.example.demo.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItemDto {
    long id;
    long productId;
    double price;
    float amount;
}
