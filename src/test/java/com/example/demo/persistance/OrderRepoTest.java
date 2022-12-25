package com.example.demo.persistance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepoTest {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Test
    public void test() {
        Product p = Product.builder()
                .attributesJson("lofasz")
                .name("MacBook")
                .build();
        productRepo.save(p);

        Customer c = Customer.builder()
                .authority(Customer.Authority.USER)
                .password("secret")
                .name("Jozsi")
                .age(12)
                .build();
        customerRepo.save(c);

        Order order = Order.builder()
                .orderStatus(Order.OrderStatus.SUBMITTED)
                .customer(customerRepo.getReferenceById(c.getId()))
                .build();

        OrderItem orderItem = OrderItem.builder()
                .amount(5)
                .price(10.0)
                .order(order)
                .product(p)
                .build();
        order.setOrderItems(Arrays.asList(orderItem));

        Order saved = orderRepo.save(order);
        Order retrieved = orderRepo.findById(saved.getId()).get();
        assertEquals(retrieved.getOrderStatus(), Order.OrderStatus.SUBMITTED);
        assertEquals(Arrays.asList(orderItem), retrieved.getOrderItems());

        OrderItem orderItem1 = orderItemRepo.findById(saved.getOrderItems().get(0).getId()).get();
        assertEquals(orderItem1, orderItem);

        orderRepo.delete(order);
        assertFalse(orderItemRepo.findById(saved.getOrderItems().get(0).getId()).isPresent());
    }
}
