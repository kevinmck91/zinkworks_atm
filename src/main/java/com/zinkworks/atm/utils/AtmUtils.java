package com.zinkworks.atm.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.zinkworks.atm.dto.Atm;
import com.zinkworks.atm.dto.Customer;
import com.zinkworks.atm.repositories.AtmRepository;
import com.zinkworks.atm.responses.CashOutputDetails;

public class AtmUtils {
	
	public boolean validateAccount(Customer customer, int inputPin) {

		if (customer.getPin() == inputPin) {
			return true;
		} else {
			return false;
		}
	}

	// Validate that the customer has sufficient balance for the transaction
	public boolean validateCustomerBalance(Customer customer, double withDrawalAmount)  {

		double customerBalance = customer.getBalance();
		double overDraft = customer.getOverdraft();

		if (customerBalance + overDraft >= withDrawalAmount) {
			return true;
		}

		return false;
	}

	// Validate that the Atm has sufficient balance for the transaction
	public boolean validateAtmAmount(Atm atm, double amount) {

		double notes_5 = atm.getNotes_5();
		double notes_10 = atm.getNotes_10();
		double notes_20 = atm.getNotes_20();
		double notes_50 = atm.getNotes_50();

		double atmAmount = notes_5*5 + notes_10*10 + notes_20*20 + notes_50*50;
		
		if (atmAmount >= amount) {
			return true;
		}
		
		return false;
	}
	
	


}
