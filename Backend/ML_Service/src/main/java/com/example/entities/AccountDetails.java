package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
	public enum accounttype{
		SAVING,
		CURRENT
		
	}
	
	private Long accountId; //Long(8 bits) is For largerInteger value then int(4 bits) datatype stores
	
	
	private Long userId;
	
	
	private String accountNumber;
	
	
	private accounttype accountType=accounttype.SAVING;
	
	
	private Long accountBalance;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public accounttype getAccountType() {
		return accountType;
	}

	public void setAccountType(accounttype accountType) {
		this.accountType = accountType;
	}

	public Long getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
	}




}
