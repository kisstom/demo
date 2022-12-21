package com.example.demo.service;

import com.example.demo.model.RegisterCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {

    private final InMemoryUserDetailsManager userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerRegistrationService(InMemoryUserDetailsManager userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public void createCustomerSecret(RegisterCustomerRequest registerCustomerRequest, Long id) {
        UserDetails user = User.withUsername(Long.toString(id))
                .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                .authorities("USER")
                .build();
        userDetailsService.createUser(user);
    }
}
