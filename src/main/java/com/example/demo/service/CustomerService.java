package com.example.demo.service;

import com.example.demo.mapper.MapStructMapper;
import com.example.demo.model.CustomerDto;
import com.example.demo.persistance.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final MapStructMapper mapStructMapper;

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(MapStructMapper mapStructMapper, CustomerRepo customerRepo) {
        this.mapStructMapper = mapStructMapper;
        this.customerRepo = customerRepo;
    }

    public CustomerDto getCustomer(long id) {
        return mapStructMapper.customerToCustomerDto(customerRepo.findById(id).get());
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        return mapStructMapper.customerToCustomerDto(
                customerRepo.save(mapStructMapper.customerDtoToCustomer(customerDto)));
    }
}
