package com.example.demo.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Value
@Builder
public class CustomerDto {
    @Nullable
    private String id;
    private String name;
    private int age;
}
