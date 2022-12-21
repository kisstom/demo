package com.example.demo.service;

import com.example.demo.persistance.Customer;
import com.example.demo.persistance.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DaoUserDetailsService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    @Autowired
    public DaoUserDetailsService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Customer customer = customerRepo.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("User not found: " + id));
        return User.withUsername(customer.getName())
                .password(customer.getPassword())
                .authorities(customer.getAuthority().name())
                .build();
    }
}
