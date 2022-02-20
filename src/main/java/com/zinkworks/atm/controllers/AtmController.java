package com.zinkworks.atm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zinkworks.atm.dto.Atm;
import com.zinkworks.atm.dto.Customer;
import com.zinkworks.atm.dto.RequestBalance;
import com.zinkworks.atm.dto.RequestWithdrawal;
import com.zinkworks.atm.exceptions.AccountNotFoundException;
import com.zinkworks.atm.exceptions.AtmAmountException;
import com.zinkworks.atm.exceptions.CustomerBalanceException;
import com.zinkworks.atm.exceptions.IncorrectPinException;
import com.zinkworks.atm.repositories.AtmRepository;
import com.zinkworks.atm.repositories.CustomerRepository;
import com.zinkworks.atm.utils.AtmUtils;

@RestController
public class AtmController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AtmRepository atmRepository;
	
	Atm atm = new Atm();
	AtmUtils AtmUtils = new AtmUtils();

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

			List<Customer> customerList = customerRepository.findByAccountNumber(requestBalance.getAccountNumber());

			if (customerList.isEmpty()) {
				throw new AccountNotFoundException("Account not found - " + requestBalance.getAccountNumber());
			}

			Customer customer = customerList.get(0);

			int inputAccountNumber = requestBalance.getAccountNumber();
			int inputPin = requestBalance.getPin();

			boolean pinValid = AtmUtils.validateAccount(customer, inputPin);

			if (pinValid) {
				return "Your Balance is €" + customer.getBalance();
			} else {
				throw new IncorrectPinException("The pin you have entered is incorrect");
			}

	}
	
	/**
	 * 
	 * @param requestWithdrawal
	 * @return String
	 * 
	 *         Method takes in JSON with customer Account Number, Pin & amount to be withdrawn. 
	 *         Retrieves the customer and the Atm data from the database.
	 *         Validate the Pin, the customers funds and the Atm funds.
	 * 
	 */
	
	@PostMapping("withdrawal/")
	public String withdrawal(@RequestBody RequestWithdrawal requestWithdrawal) {

		// Get the Customer and Atm from the database
		List<Customer> customerList = customerRepository.findByAccountNumber(requestWithdrawal.getAccountNumber());
		List<Atm> atmList = atmRepository.findAll();

		if (customerList.isEmpty()) {
			throw new AccountNotFoundException("Account not found - " + requestWithdrawal.getAccountNumber());
		}
		
		Customer customer = customerList.get(0);
		atm = atmList.get(0);

		// Set the Atm contents for use in the program
		atm.setNotes_5(atmList.get(0).getNotes_5());
		atm.setNotes_10(atmList.get(0).getNotes_10());
		atm.setNotes_20(atmList.get(0).getNotes_20());
		atm.setNotes_50(atmList.get(0).getNotes_50());

		if (customerList.isEmpty()) {
			throw new AccountNotFoundException("Account not found - " + requestWithdrawal.getAccountNumber());
		}

		int inputAccountNumber = requestWithdrawal.getAccountNumber();
		int inputPin = requestWithdrawal.getPin();

		// Validate if the transaction can proceed
		boolean pinValid = AtmUtils.validateAccount(customer, inputPin);
		boolean customerBalanceValid = AtmUtils.validateCustomerBalance(customer, requestWithdrawal.getAmount());
		boolean atmAmountValid = AtmUtils.validateAtmAmount(atm, requestWithdrawal.getAmount());

		if (!pinValid) {
			throw new IncorrectPinException("The pin you have entered is incorrect");
		} else if (!customerBalanceValid) {
			throw new CustomerBalanceException("You have insufficient funds for this transaction");
		} else if (!atmAmountValid) {
			throw new AtmAmountException("The ATM has insufficient funds for this transaction");
		} else {
			debitAccount(customer, atm, requestWithdrawal.getAmount());
			// Debit the ATM;
			// return the completed object
			return null;

		}
		

	}
	

	private void debitAccount(Customer customer, Atm atm, double amount) {
		
		double balance = customer.getBalance();
		double overdraft = customer.getOverdraft();
		
		System.out.println("\nbal: " + balance);
		System.out.println("od: " + overdraft);

		if(balance - amount <= 0) {
			amount = amount - balance;
			balance = 0;
			overdraft = overdraft - amount;
		} else {
			balance = balance - amount;
		}
		
		customer.setBalance(balance);
		customer.setOverdraft(overdraft);
		
		System.out.println("\nbal: " + balance);
		System.out.println("od: " + overdraft);		
		
		customerRepository.save(customer);
		
	}



}
