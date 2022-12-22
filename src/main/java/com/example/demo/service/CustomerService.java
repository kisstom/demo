package com.example.demo.service;

import com.example.demo.mapper.MapStructMapper;
import com.example.demo.model.CustomerDto;
import com.example.demo.model.RegisterCustomerRequest;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final MapStructMapper mapStructMapper;

    private final CustomerRepo customerRepo;

    private final PasswordEncoder passwordEncoder;

    public CustomerDto getCustomer(long id) {
        return mapStructMapper.customerToCustomerDto(customerRepo.findById(id).get());
    }

    public CustomerDto createCustomer(RegisterCustomerRequest registerCustomerRequest) {
        Customer c = Customer.builder()
                .age(registerCustomerRequest.getAge())
                .name(registerCustomerRequest.getName())
                .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                .authority(Customer.Authority.USER)
                .build();
        return mapStructMapper.customerToCustomerDto(customerRepo.save(c));
    }
}
