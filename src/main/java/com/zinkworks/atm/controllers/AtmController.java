package com.zinkworks.atm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.zinkworks.atm.responses.BalanceDetails;
import com.zinkworks.atm.responses.CashOutputDetails;
import com.zinkworks.atm.responses.TransactionDetails;
import com.zinkworks.atm.utils.AtmUtils;

@RestController
public class AtmController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AtmRepository atmRepository;

	Atm atm = new Atm();
	AtmUtils AtmUtils = new AtmUtils();
	TransactionDetails transactionDetails = new TransactionDetails();

	/**
	 * 
	 * @param requestBalance
	 * @return String
	 * 
	 *         Method takes in JSON with customer Account Number & Pin. Retrieves
	 *         that customer from the database. Validate the Pin number for
	 *         customer. Outputs the balance for that customer
	 * 
	 */

	@PostMapping("balance/")
	public BalanceDetails checkBalance(@RequestBody RequestBalance requestBalance) {

		BalanceDetails balanceDetails = new BalanceDetails();

		List<Customer> customerList = customerRepository.findByAccountNumber(requestBalance.getAccountNumber());

		if (customerList.isEmpty()) {
			throw new AccountNotFoundException("Account not found - " + requestBalance.getAccountNumber());
		}

		Customer customer = customerList.get(0);

		int inputAccountNumber = requestBalance.getAccountNumber();
		int inputPin = requestBalance.getPin();

		boolean pinValid = AtmUtils.validateAccount(customer, inputPin);

		if (pinValid) {
			balanceDetails.setBalance(customer.getBalance());
			balanceDetails.setOverdraft(customer.getOverdraft());
			return balanceDetails;
		} else {
			throw new IncorrectPinException("The pin you have entered is incorrect");
		}

	}

	/**
	 * 
	 * @param requestWithdrawal
	 * @return String
	 * 
	 *         Method takes in JSON with customer Account Number, Pin & amount to be
	 *         withdrawn. Retrieves the customer and the Atm data from the database.
	 *         Validate the Pin, the customers funds and the Atm funds.
	 * 
	 */

	@PostMapping("withdrawal/")
	public TransactionDetails withdrawal(@RequestBody RequestWithdrawal requestWithdrawal) {

		// Get the Customer and Atm from the database
		List<Customer> customerList = customerRepository.findByAccountNumber(requestWithdrawal.getAccountNumber());
		List<Atm> atmList = atmRepository.findAll();

		if (customerList.isEmpty()) {
			throw new AccountNotFoundException("Account not found - " + requestWithdrawal.getAccountNumber());
		}

		Customer customer = customerList.get(0);
		atm = atmList.get(0);

		// Set the Atm contents locally for use in the program
		atm.setNotes_5(atmList.get(0).getNotes_5());
		atm.setNotes_10(atmList.get(0).getNotes_10());
		atm.setNotes_20(atmList.get(0).getNotes_20());
		atm.setNotes_50(atmList.get(0).getNotes_50());

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

			// Proceed with transaction
			debitAccount(customer, requestWithdrawal.getAmount());
			CashOutputDetails cashOutputDetails = debitAtm(atm, requestWithdrawal.getAmount()); // Debit the ATM;

			// Generate details object that will be returned to the user
			transactionDetails.setAccountNumber(requestWithdrawal.getAccountNumber());
			transactionDetails.setWithdrawalAmount(requestWithdrawal.getAmount());
			transactionDetails.setBalanceDetails(
					checkBalance(new RequestBalance(requestWithdrawal.getAccountNumber(), requestWithdrawal.getPin())));
			transactionDetails.setMessage("Transaction Successful");
			transactionDetails.setCashOutputDetails(cashOutputDetails);

			return transactionDetails;

		}

	}

	/**
	 * 
	 * @return Atm
	 * 
	 *         Method queries the database and returns the Atm details
	 * 
	 */

	@GetMapping("atm/")
	public Atm atmDetails() {

		List<Atm> atmList = atmRepository.findAll();

		return atmList.get(0);
	}



	private void debitAccount(Customer customer, double amount) {
		
		double balance = customer.getBalance();
		double overdraft = customer.getOverdraft();
		
		if(balance - amount <= 0) {
			amount = amount - balance;
			balance = 0;
			overdraft = overdraft - amount;
		} else {
			balance = balance - amount;
		}
		
		customer.setBalance(balance);
		customer.setOverdraft(overdraft);
		
		customerRepository.save(customer);
		
	}
	
	// ATM has sufficient cash at this point
	public CashOutputDetails debitAtm(Atm atm, double amountRemaining) {

		int notes_50 = atm.getNotes_50();
		int notes_20 = atm.getNotes_20();
		int notes_10 = atm.getNotes_10();
		int notes_5 = atm.getNotes_5();

		int outputNotes_50 = 0;
		int outputNotes_20 = 0;
		int outputNotes_10 = 0;
		int outputNotes_5 = 0;
		
		for(int i = 0; i <= notes_50 && notes_50 >= 1; i++ ) {
			if(amountRemaining >= 50) {
				amountRemaining = amountRemaining - 50;
				outputNotes_50 ++;
				notes_50 --;
			} else {
				break;
			}
		}
		
		for(int i = 0; i <= notes_20 && notes_20 >= 1; i++ ) {
			if(amountRemaining >= 20) {
				amountRemaining = amountRemaining - 20;
				outputNotes_20 ++;
				notes_20 --;
			} else {
				break;
			}
		}
		
		for(int i = 0; i <= notes_10 && notes_10 >= 1; i++ ) {
			if(amountRemaining >= 10) {
				amountRemaining = amountRemaining - 10;
				outputNotes_10 ++;
				notes_10 --;
			} else {
				break;
			}
		}
		
		for(int i = 0; i <= notes_5 && notes_5 > 1; i++ ) {
			if(amountRemaining >= 5) {
				amountRemaining = amountRemaining - 5;
				outputNotes_5 ++;
				notes_5 --;
			} else {
				break;
			}
		}
		
		if(amountRemaining > 0) {
			throw new AtmAmountException("Atm cannot dispense any combinaiton of notes to meet withdrawal amount");
		}
		
		CashOutputDetails cashOutputDetails = new CashOutputDetails(outputNotes_5, outputNotes_10, outputNotes_20, outputNotes_50);

		atm.setNotes_5(notes_5);
		atm.setNotes_10(notes_10);
		atm.setNotes_20(notes_20);
		atm.setNotes_50(notes_50);
		
		atmRepository.save(atm);
		
		return cashOutputDetails;
	}

	
	

}
