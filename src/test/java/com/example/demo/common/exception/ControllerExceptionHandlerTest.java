package com.example.demo.common.exception;

import com.example.demo.config.SecurityConfig;
import com.example.demo.controller.OrderController;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.Order;
import com.example.demo.service.OrderService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import({SecurityConfig.class})
public class ControllerExceptionHandlerTest {

    private static long CUSTOMER_ID = 1;

    private static String PASSWORD = "secret";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        when(userDetailsService.loadUserByUsername(Long.toString(CUSTOMER_ID))).
                thenReturn(User
                        .withUsername(Long.toString(CUSTOMER_ID))
                        .password(bCryptPasswordEncoder.encode(PASSWORD))
                        .authorities(Customer.Authority.USER.name())
                        .build());

        when(orderService.getOrder(anyLong())).thenThrow(new ResourceNotFoundException("Unknown order id."));
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/api/v1/orders/1")
                .with(httpBasic(Long.toString(CUSTOMER_ID), PASSWORD))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
