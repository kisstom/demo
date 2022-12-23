package com.example.demo.model;

import com.example.demo.persistance.Order;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDto {
    long id;
    long customerId;
    Order.OrderStatus orderStatus;
}
