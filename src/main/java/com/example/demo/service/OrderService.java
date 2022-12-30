package com.example.demo.service;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.model.CreateOrderRequest;
import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderItemDto;
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
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unknown order id: " + id));
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .orderStatus(order.getOrderStatus()).build();
    }

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .orderStatus(Order.OrderStatus.CART)
                .customer(customerRepo.getReferenceById(createOrderRequest.getCustomerId()))
                .build();

        List<OrderItem> orderItems = createOrderRequest.getOrderItems().stream().map(orderItemDto -> {
            return OrderItem.builder()
                    .amount(orderItemDto.getAmount())
                    .price(orderItemDto.getPrice())
                    .product(productRepo.getReferenceById(orderItemDto.getProductId()))
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
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unknown order id: " + id));
        order.setOrderStatus(status);
        Order savedOrder = orderRepo.save(order);
        return OrderDto.builder()
                .id(savedOrder.getId())
                .orderStatus(savedOrder.getOrderStatus())
                .customerId(savedOrder.getCustomer().getId()).build();
    }

    @Transactional
    public OrderDto addToCart(long id, OrderItemDto orderItemDto) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unknown order id: " + id));
        if (order.getOrderStatus() != Order.OrderStatus.CART) {
            throw new RuntimeException();
        }

        OrderItem orderItem = OrderItem.builder()
                .product(productRepo.getReferenceById(orderItemDto.getProductId()))
                .order(orderRepo.getReferenceById(id))
                .price(orderItemDto.getPrice())
                .amount(orderItemDto.getAmount())
                .build();
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepo.save(order);
        return OrderDto.builder()
                .id(savedOrder.getId())
                .customerId(savedOrder.getCustomer().getId())
                .orderStatus(savedOrder.getOrderStatus())
                .build();
    }
}
