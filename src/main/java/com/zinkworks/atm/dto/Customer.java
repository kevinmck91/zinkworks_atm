package com.zinkworks.atm.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Customer {
	
	@Id
	private int 	accountNumber;
	private int 	pin;
	private double 	balance;
	private double 	overdraft;
	
	public Customer() {
	
	}
	
	public Customer(int accountNumber, int pin, double balance, double overdraft) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
		this.overdraft = overdraft;
	}
	
	public Integer getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}

}
