package com.example.demo.mapper;

import com.example.demo.model.CustomerDto;
import com.example.demo.persistance.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MapStructMapperTest {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Test
	void testCustomerDtoToCustomer() {
		long id = 1;
		int age = 12;
		String name = "jozsi";
		CustomerDto customerDto = CustomerDto.builder()
				.age(age)
				.id(id)
				.name(name)
				.build();

		Customer customer = mapStructMapper.customerDtoToCustomer(customerDto);
		assertEquals(id, customer.getId());
		assertEquals(age, customer.getAge());
		assertEquals(name, customer.getName());
	}

	@Test
	void testCustomerToCustomerDto() {
		long id = 1;
		int age = 12;
		String name = "jozsi";
		Customer customer = Customer.builder()
				.age(age)
				.name(name)
				.id(id)
				.build();

		CustomerDto customerDto = mapStructMapper.customerToCustomerDto(customer);
		assertEquals(id, customerDto.getId());
		assertEquals(age, customerDto.getAge());
		assertEquals(name, customerDto.getName());
	}

}
