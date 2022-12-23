package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.CreateProductRequest;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.CustomerRepo;
import com.example.demo.service.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import({SecurityConfig.class})
public class ProductControllerTest {

    private static final long ADMIN_ID = 2;

    private static final String ADMIN_PASSWORD = "admin_secret";

    private static long CUSTOMER_ID = 1;

    private static String PASSWORD = "secret";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private ProductService productService;

    @MockBean
    private CustomerRepo customerRepo;

    @BeforeEach
    public void setup() {
        when(userDetailsService.loadUserByUsername(Long.toString(CUSTOMER_ID))).
                thenReturn(User
                        .withUsername(Long.toString(CUSTOMER_ID))
                        .password(bCryptPasswordEncoder.encode(PASSWORD))
                        .authorities(Customer.Authority.USER.name())
                        .build());

        when(userDetailsService.loadUserByUsername(Long.toString(ADMIN_ID))).
                thenReturn(User
                        .withUsername(Long.toString(ADMIN_ID))
                        .password(bCryptPasswordEncoder.encode(ADMIN_PASSWORD))
                        .authorities(Customer.Authority.ADMIN.name())
                        .build());
    }

    @Test
    void testUserRoleDeniedForProductCreation() throws Exception {
        Gson gson = new Gson();
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .attributesJson("")
                .name("Macbook")
                .build();
        String json = gson.toJson(createProductRequest);

        mockMvc.perform(post("/api/v1/products/add")
                .with(httpBasic(Long.toString(CUSTOMER_ID), PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void testUserRoleAcceptedForProductGet() throws Exception {
        mockMvc.perform(get("/api/v1/products/1")
                .with(httpBasic(Long.toString(CUSTOMER_ID), PASSWORD))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAdminRoleAcceptedForProductCreation() throws Exception {
        Gson gson = new Gson();
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .attributesJson("")
                .name("Macbook")
                .build();
        String json = gson.toJson(createProductRequest);

        mockMvc.perform(post("/api/v1/products/add")
                .with(httpBasic(Long.toString(ADMIN_ID), ADMIN_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
