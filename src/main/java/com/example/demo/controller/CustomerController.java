package com.example.demo.controller;

import com.example.demo.model.CustomerDto;
import com.example.demo.model.RegisterCustomerRequest;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(path = "/{id}")
    public CustomerDto getCustomer(@PathVariable int id) {
        return customerService.getCustomer(id);
    }

    @PostMapping(path = "/register")
    public CustomerDto registerCustomer(@RequestBody RegisterCustomerRequest registerCustomerRequest) {
        return customerService.createCustomer(registerCustomerRequest);
    }
}
