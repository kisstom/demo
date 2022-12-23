package com.example.demo.mapper;

import com.example.demo.model.CustomerDto;
import com.example.demo.model.ProductDto;
import com.example.demo.persistance.Customer;
import com.example.demo.persistance.Product;
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

	@Test
	public void testProductToProductDto() {
		String json = "";
		String name = "name";
		long id = 1;
		Product product = Product.builder()
				.attributesJson(json)
				.name(name)
				.id(id)
				.build();

		ProductDto productDto = mapStructMapper.productToProductDto(product);
		assertEquals(json, productDto.getAttributesJson());
		assertEquals(name, productDto.getName());
		assertEquals(id, productDto.getId());
	}
}
