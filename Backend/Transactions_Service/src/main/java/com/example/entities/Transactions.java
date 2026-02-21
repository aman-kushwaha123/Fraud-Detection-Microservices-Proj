package com.example.entities;

import java.awt.GraphicsDevice;
import java.security.PublicKey;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class Transactions {
	enum txn_types{
		UPI,
		CARD,
		ATM,
	}
	
	public enum Status{
		SUCCESS,
		FAILED
	}
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long txnId; //Unique Transaction ID
	
	@Column(columnDefinition = "json")             //Store JSON in MySQL
	private String account; //Identity who made transaction
	
	@Column
	private Long fromAccountId;
	
	

	@Column
	private Long amount; //Feature for anomaly
	
	@Enumerated(EnumType.STRING)
	private txn_types txnType=txn_types.UPI; 
	
	
	@Enumerated(EnumType.STRING)
	private Status status=Status.FAILED; 
	
	@Column
	private String toAccFullname;


	@Column
	private Long toAccountId;

	@Column(columnDefinition = "json")  
	private String location;						 //feature for anomaly detection

	@Column(columnDefinition = "json")  
	private String device;						    //Detect Unknown Devices
	
	
	//@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT TIMESTAMP")
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime timeStamp;                //Detect unusual time patterns
	
	//merchant (optional) inside it can be amazon,flipkart,zomato restaurant,swiggy,etc
	
	@Column
	private Boolean isFraud=false;
	
	
	@Column
	private Long userId;
	
	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	
	public String getToAccFullname() {
		return toAccFullname;
	}

	public void setToAccFullname(String toAccFullname) {
		this.toAccFullname = toAccFullname;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
