package com.zinkworks.atm.responses;

public class BalanceDetails {

	private double 	balance;
	private double 	overdraft;

	public BalanceDetails() {

	}

	public BalanceDetails(double balance, double overdraft) {
		this.balance = balance;
		this.overdraft = overdraft;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	@Override
	public String toString() {
		return "Balance [balance=" + balance + ", overdraft=" + overdraft + "]";
	}
	
	
	
}
