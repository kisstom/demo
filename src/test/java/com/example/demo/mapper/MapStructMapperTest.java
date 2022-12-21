package com.example.demo.mapper;

import com.example.demo.controller.CustomerController;
import com.example.demo.mapper.MapStructMapper;
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
	void testMapStruct() {
		long id = 1;
		int age = 12;
		String name = "jozsi";
		CustomerDto customerDto = new CustomerDto();
		customerDto.setAge(age);
		customerDto.setId(id);
		customerDto.setName(name);

		Customer customer = mapStructMapper.customerDtoToCustomer(customerDto);
		assertEquals(id, customer.getId());
		assertEquals(age, customer.getAge());
		assertEquals(name, customer.getName());
	}

}
