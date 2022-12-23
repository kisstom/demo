package com.example.demo.mapper;

import com.example.demo.model.CustomerDto;
import com.example.demo.model.ProductDto;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);

    ProductDto productToProductDto(Product product);
}
