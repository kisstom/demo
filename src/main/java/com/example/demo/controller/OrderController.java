package com.example.demo.controller;

import com.example.demo.model.CreateOrderRequest;
import com.example.demo.model.OrderDto;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/{id}")
    public OrderDto getOrder(@PathVariable int id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }
}
