package com.example.demo.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterCustomerRequest {
    String name;
    int age;
    String password;
}
