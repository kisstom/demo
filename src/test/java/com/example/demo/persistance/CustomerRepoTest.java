package com.example.demo.persistance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepoTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void testSave() {
        int age = 1;
        String name = "name";
        String password = "password";
        Customer.Authority authority = Customer.Authority.USER;

        Customer c = Customer.builder()
                .age(age)
                .name(name)
                .password(password)
                .authority(authority).build();
        Customer saved = customerRepo.save(c);
        Customer retrieved = customerRepo.findById(saved.getId()).get();
        assertEquals(age, retrieved.getAge());
        assertEquals(name, retrieved.getName());
        assertEquals(password, retrieved.getPassword());
        assertEquals(authority, retrieved.getAuthority());
    }
}
