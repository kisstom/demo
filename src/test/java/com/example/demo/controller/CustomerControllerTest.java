package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.CustomerDto;
import com.example.demo.model.RegisterCustomerRequest;
import com.example.demo.service.CustomerService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecurityConfig.class)
@WebMvcTest(controllers = {CustomerController.class})
@ComponentScan(basePackages = "com.example.demo")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void testPost() throws Exception {
        // given
        String name = "jozsi";
        int age = 1;
        String password = "perec";

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                RegisterCustomerRequest registerCustomerRequest = (RegisterCustomerRequest) invocationOnMock.getArgument(0);
                CustomerDto customerDto = CustomerDto.builder()
                        .age(registerCustomerRequest.getAge())
                        .name(registerCustomerRequest.getName())
                        .id(1L).build();
                return customerDto;
            }
        }).when(customerService).createCustomer(any());

        Gson gson = new Gson();

        RegisterCustomerRequest registerCustomerRequest = RegisterCustomerRequest.builder()
                .name(name)
                .age(age)
                .password(password).build();

        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .name(name)
                .age(age).build();

        String json = gson.toJson(registerCustomerRequest);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/customers/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(customerDto, gson.fromJson(mvcResult.getResponse().getContentAsString(), CustomerDto.class));
    }
}
