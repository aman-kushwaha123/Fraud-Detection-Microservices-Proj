package com.example.entities;

import com.example.entities.AccountDetails.accounttype;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//creating DTO(Data transfer object) used for creation json or object datatype
//we can also create account entity which will not save in db we can use it for creating object

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
	public enum accounttype{
		SAVINGS,
		CURRENT
		
	}
	
	private Long accountId; //Long(8 bits) is For largerInteger value then int(4 bits) datatype stores
	
	
	private Long userId;
	
	private String username;
	
	private String mobileNo;
	
	private String accountNumber;
	
	private accounttype accountType=accounttype.SAVINGS;
	
	private Long accountBalance;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

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
