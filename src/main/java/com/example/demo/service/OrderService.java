package com.example.demo.service;

import com.example.demo.model.CreateOrderRequest;
import com.example.demo.model.OrderDto;
import com.example.demo.persistance.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    private final ProductRepo productRepo;

    private final CustomerRepo customerRepo;

    public OrderDto getOrder(long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Unknown order id: " + id));
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .orderStatus(order.getOrderStatus()).build();
    }

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        Customer customer = customerRepo.findById(createOrderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Unknown customer id: " + createOrderRequest.getCustomerId()));

        Order order = Order.builder()
                .orderStatus(Order.OrderStatus.CART)
                .customer(customer)
                .build();

        List<OrderItem> orderItems = createOrderRequest.getOrderItems().stream().map(orderItemDto -> {
            Product product = productRepo.findById(orderItemDto.getProductId()).
                    orElseThrow(() -> new RuntimeException("Unknown product id: " + orderItemDto.getProductId()));

            return OrderItem.builder()
                    .amount(orderItemDto.getAmount())
                    .price(orderItemDto.getPrice())
                    .product(product)
                    .order(order)
                    .build();

        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        return OrderDto.builder()
                .id(savedOrder.getId())
                .orderStatus(savedOrder.getOrderStatus())
                .customerId(savedOrder.getCustomer().getId()).build();
    }

    @Transactional
    public OrderDto updateStatus(long id, Order.OrderStatus status) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Unknown order id: " + id));
        order.setOrderStatus(status);
        Order savedOrder = orderRepo.save(order);
        return OrderDto.builder()
                .id(savedOrder.getId())
                .orderStatus(savedOrder.getOrderStatus())
                .customerId(savedOrder.getCustomer().getId()).build();
    }
}
