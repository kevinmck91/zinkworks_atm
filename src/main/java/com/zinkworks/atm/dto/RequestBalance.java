package com.zinkworks.atm.dto;

public class RequestBalance {
	
	int accountNumber;
	int pin;
	
	RequestBalance(){
		
	}

	public RequestBalance(int accountNumber, int pin) {
		this.accountNumber = accountNumber;
		this.pin = pin;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	

}
