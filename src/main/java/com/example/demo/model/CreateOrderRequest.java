package com.example.demo.model;

import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder
public class CreateOrderRequest {
    long customerId;
    List<OrderItemDto> orderItems;
}
