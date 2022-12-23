package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.CreateOrderRequest;
import com.example.demo.model.CustomerDto;
import com.example.demo.model.OrderItemDto;
import com.example.demo.model.RegisterCustomerRequest;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.Order;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecurityConfig.class)
@WebMvcTest(controllers = {OrderController.class})
@ComponentScan(basePackages = "com.example.demo")
public class OrderControllerTest {

    private static long CUSTOMER_ID = 1;

    private static String PASSWORD = "secret";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() {
        when(userDetailsService.loadUserByUsername(Long.toString(CUSTOMER_ID))).
                thenReturn(User
                        .withUsername(Long.toString(CUSTOMER_ID))
                        .password(bCryptPasswordEncoder.encode(PASSWORD))
                        .authorities(Customer.Authority.USER.name())
                        .build());
    }

    @Test
    public void testPatch() throws Exception {
        // given
        Gson gson = new Gson();
        String json = gson.toJson(Order.OrderStatus.SUBMITTED);

        MvcResult mvcResult = mockMvc.perform(patch("/api/v1/orders/update_status/1")
                .with(httpBasic(Long.toString(CUSTOMER_ID), PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        verify(orderService).updateStatus(CUSTOMER_ID, Order.OrderStatus.SUBMITTED);
    }
}
