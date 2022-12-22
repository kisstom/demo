package com.example.demo.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerDto {
    long id;
    String name;
    int age;
}

