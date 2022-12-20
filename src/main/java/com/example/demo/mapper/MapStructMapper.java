package com.example.demo.mapper;

import com.example.demo.model.CustomerDto;
import com.example.demo.persistance.Customer;
import org.springframework.stereotype.Component;

@Component
public interface MapStructMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
