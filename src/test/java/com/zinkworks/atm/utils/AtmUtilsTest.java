package com.zinkworks.atm.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import com.zinkworks.atm.dto.Atm;
import com.zinkworks.atm.dto.Customer;

@SpringBootTest
public class AtmUtilsTest {
	
	AtmUtils atmUtils;
	Atm atm;
	Customer customer;
	
	@BeforeEach
	public void setUp() throws Exception {
		atm = new Atm(1, 10,10,10,10);
		customer = new Customer(123456789,1234,800,200);
	}
	
	@Test
	void validateAccountTest() {
		assertEquals(atmUtils.validateAccount(customer, 1234), true);
		assertEquals(atmUtils.validateAccount(customer, 9999), false);
	}
	
	@Test
	void validateCustomerBalanceTest() {
		assertEquals(atmUtils.validateCustomerBalance(customer, 800), true);
		assertEquals(atmUtils.validateCustomerBalance(customer, 1500), false);
	}
	
	@Test
	void validateAtmAmountTest() {
		assertEquals(atmUtils.validateAtmAmount(atm, 800), true);
		assertEquals(atmUtils.validateAtmAmount(atm, 850), true);
		assertEquals(atmUtils.validateAtmAmount(atm, 1000), false);
	}

}
