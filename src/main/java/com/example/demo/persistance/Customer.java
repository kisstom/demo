package com.example.demo.persistance;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;
}
