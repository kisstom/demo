package com.example.demo.integration;

import com.example.demo.model.*;
import com.example.demo.persistance.Order;
import com.example.demo.persistance.OrderItemRepo;
import com.example.demo.persistance.OrderRepo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BasicFlowIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Test
    public void test() throws Exception {
        String name = "jozsi";
        int age = 1;
        String password = "perec";

        Gson gson = new Gson();

        RegisterCustomerRequest registerCustomerRequest = RegisterCustomerRequest.builder()
                .name(name)
                .age(age)
                .password(password).build();

        String json = gson.toJson(registerCustomerRequest);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/customers/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CustomerDto customerDto = gson.fromJson(mvcResult.getResponse().getContentAsString(), CustomerDto.class);

        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .attributesJson("")
                .name("MacBook")
                .build();

        json = gson.toJson(createProductRequest);

        mvcResult = mockMvc.perform(
                post("/api/v1/products").
                        with(httpBasic(Long.toString(customerDto.getId()), password))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProductDto productDto = gson.fromJson(mvcResult.getResponse().getContentAsString(), ProductDto.class);

        OrderItemDto orderItemDto = OrderItemDto.builder()
                .amount(2)
                .price(100.0)
                .productId(productDto.getId())
                .build();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .orderItems(Arrays.asList(orderItemDto))
                .customerId(customerDto.getId())
                .build();
        json = gson.toJson(createOrderRequest);

        mvcResult = mockMvc.perform(
                post("/api/v1/orders").
                        with(httpBasic(Long.toString(customerDto.getId()), password))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto orderDto = gson.fromJson(mvcResult.getResponse().getContentAsString(), OrderDto.class);

        Order order = orderRepo.findById(orderDto.getId()).get();
        assertEquals(order.getOrderItems().get(0).getAmount(), 2);
    }
}
