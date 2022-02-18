package com.zinkworks.atm.utils;

import com.zinkworks.atm.dto.Customer;

public class AtmUtils {

	public boolean validateAccount(Customer customer, int inputPin) {

		if (customer.getPin() == inputPin) {
			return true;
		} else {
			return false;
		}
	}
	
}
