package com.example.entities;



import java.time.LocalDateTime;
import java.util.function.LongBinaryOperator;

import org.apache.catalina.valves.LoadBalancerDrainingValve;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class AccountDetails {
	enum accounttype{
		SAVING,
		CURRENT
		
	}
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountId; //Long(8 bits) is For largerInteger value then int(4 bits) datatype stores
	
	@Column
	private Long userId;
	
	@Column
	private String username;
	
	
	@Column
	private String mobileNo;
	
	@Column
	private String accountNumber;
	
	@Enumerated(EnumType.STRING)
	private accounttype accountType=accounttype.SAVING;
	
	@Column
	private Long accountBalance;
	
	@Column
	private String fullName;
	
	@CreationTimestamp
	@Column(updatable=false)  
	private LocalDateTime createdAt;
	
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String  getAccountNumber() {
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
