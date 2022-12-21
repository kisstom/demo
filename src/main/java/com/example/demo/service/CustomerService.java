package com.example.demo.service;

import com.example.demo.mapper.MapStructMapper;
import com.example.demo.model.CustomerDto;
import com.example.demo.model.RegisterCustomerRequest;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final MapStructMapper mapStructMapper;

    private final CustomerRepo customerRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(MapStructMapper mapStructMapper, CustomerRepo customerRepo,
                           PasswordEncoder passwordEncoder) {
        this.mapStructMapper = mapStructMapper;
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDto getCustomer(long id) {
        return mapStructMapper.customerToCustomerDto(customerRepo.findById(id).get());
    }

    public CustomerDto createCustomer(RegisterCustomerRequest registerCustomerRequest) {
        Customer c = new Customer();
        c.setName(registerCustomerRequest.getName());
        c.setAge(registerCustomerRequest.getAge());
        c.setPassword(passwordEncoder.encode(registerCustomerRequest.getPassword()));
        c.setAuthority(Customer.Authority.USER);
        return mapStructMapper.customerToCustomerDto(customerRepo.save(c));
    }
}
