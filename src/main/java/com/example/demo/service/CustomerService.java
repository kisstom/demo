package com.example.demo.service;

import com.example.demo.mapper.MapStructMapper;
import com.example.demo.model.CustomerDto;
import com.example.demo.model.RegisterCustomerRequest;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final MapStructMapper mapStructMapper;

    private final CustomerRepo customerRepo;

    private final CustomerRegistrationService customerRegistrationService;

    @Autowired
    public CustomerService(MapStructMapper mapStructMapper, CustomerRepo customerRepo,
                           CustomerRegistrationService customerRegistrationService) {
        this.mapStructMapper = mapStructMapper;
        this.customerRepo = customerRepo;
        this.customerRegistrationService = customerRegistrationService;
    }

    public CustomerDto getCustomer(long id) {
        return mapStructMapper.customerToCustomerDto(customerRepo.findById(id).get());
    }

    public CustomerDto createCustomer(RegisterCustomerRequest registerCustomerRequest) {
        Customer c = new Customer();
        c.setName(registerCustomerRequest.getName());
        c.setAge(registerCustomerRequest.getAge());
        Customer saved = customerRepo.save(c);
        customerRegistrationService.createCustomerSecret(registerCustomerRequest, saved.getId());
        return mapStructMapper.customerToCustomerDto(saved);
    }
}
