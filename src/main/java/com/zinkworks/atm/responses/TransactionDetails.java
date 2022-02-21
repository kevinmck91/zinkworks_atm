package com.zinkworks.atm.responses;

public class TransactionDetails {

	int AccountNumber;
	double withdrawalAmount;
	BalanceDetails balanceDetails;
	CashOutputDetails cashOutputDetails;
	String message;

	public TransactionDetails() {

	}

	public TransactionDetails(int accountNumber, double withdrawalAmount, BalanceDetails balanceDetails, CashOutputDetails cashOutputDetails, String message) {
		super();
		AccountNumber = accountNumber;
		this.withdrawalAmount = withdrawalAmount;
		this.balanceDetails = balanceDetails;
		this.cashOutputDetails = cashOutputDetails;
		this.message = message;
	}

	public int getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		AccountNumber = accountNumber;
	}

	public double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public BalanceDetails getBalanceDetails() {
		return balanceDetails;
	}

	public void setBalanceDetails(BalanceDetails balanceDetails) {
		this.balanceDetails = balanceDetails;
	}

	public CashOutputDetails getCashOutputDetails() {
		return cashOutputDetails;
	}

	public void setCashOutputDetails(CashOutputDetails cashOutputDetails) {
		this.cashOutputDetails = cashOutputDetails;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
