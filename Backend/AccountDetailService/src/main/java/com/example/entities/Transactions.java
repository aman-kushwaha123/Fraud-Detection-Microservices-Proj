package com.example.entities;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
	enum txn_types{
		UPI,
		CARD,
		ATM,
	}

	private Long txnId; //Unique Transaction ID
	
	
	private String account; //Identity who made transaction
	
	
	

	private Long amount; //Feature for anomaly
	
	
	private txn_types txnType=txn_types.UPI; 

	
	private String location;						 //feature for anomaly detection

	
	private String device;						    //Detect Unknown Devices
	
	
	private String ipAddress;                      //Detect high risk in ip addresses
	
	
	private LocalDateTime timeStamp;                //Detect unusual time patterns
	
	
	private Boolean isFraud=false;
	
	private Long userId;
	
	private Long toAccountId;
	
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	
	public Long getToAccountId() {
		return toAccountId;
	}


	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	

	public Long getTxnId() {
		return txnId;
	}


	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public txn_types getTxnType() {
		return txnType;
	}


	public void setTxnType(txn_types txnType) {
		this.txnType = txnType;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDevice() {
		return device;
	}


	public void setDevice(String device) {
		this.device = device;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}


	public Boolean getIsFraud() {
		return isFraud;
	}


	public void setIsFraud(Boolean isFraud) {
		this.isFraud = isFraud;
	}


	
}
