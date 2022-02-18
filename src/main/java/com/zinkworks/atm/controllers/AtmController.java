package com.zinkworks.atm.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zinkworks.atm.dto.Customer;
import com.zinkworks.atm.dto.RequestBalance;
import com.zinkworks.atm.repositories.CustomerRepository;
import com.zinkworks.atm.utils.AtmUtils;

@RestController
public class AtmController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AtmUtils AtmUtils;

	/**
	 * 
	 * @param requestBalance
	 * @return String
	 * 
	 *         Method takes in JSON with customer Account Number & Pin. 
	 *         Retrieves that customer from the database.
	 *         Validate the Pin number for customer.
	 *         Outputs the balance for that customer
	 * 
	 */

	@PostMapping("balance/")
	public String checkBalance(@RequestBody RequestBalance requestBalance) {

		try {

			List<Customer> customerList = customerRepository.findByAccountNumber(requestBalance.getAccountNumber());

			if (customerList.isEmpty()) {
				return "Account does not exist";
			}

			Customer customer = customerList.get(0);

			int inputAccountNumber = requestBalance.getAccountNumber();
			int inputPin = requestBalance.getPin();

			boolean valid = AtmUtils.validateAccount(customer, inputAccountNumber);

			if (valid) {
				return "Your Balance is €" + customer.getBalance();
			} else {
				return "Pin is incorrect";
			}

		} catch (Exception e) {
			return "Error accessing account";
		}

	}

}
