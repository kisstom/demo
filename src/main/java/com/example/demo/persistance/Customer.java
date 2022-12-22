package com.example.demo.persistance;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
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

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Authority authority;

    public enum Authority {
        USER, ADMIN;
    }
}

