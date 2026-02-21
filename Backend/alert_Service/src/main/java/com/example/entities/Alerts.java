package com.example.entities;

import java.security.KeyStore.PrivateKeyEntry;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Table;

@Table
@Entity
public class Alerts {
	 public enum Status{
		SENT,
		READ
	}
	 
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long alertId;						
	
	@Column
	private Long txnId;					//Link alert to txn
	
	@Column
	private Long userId;					//Who received alert Notification
	
	
	@ElementCollection
	@CollectionTable(
			name="alert_messages",
			joinColumns= @JoinColumn(name="alert_id")
			)
	@Column
	private List<String> alertMessage;  				//Notification content
	
	

	@Column
	@CreationTimestamp
	private LocalDateTime timeStamp;
	
	@Enumerated(EnumType.STRING)
	private Status status=Status.READ;					//SENT/READ		
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<String> getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(List<String> alertMessage) {
		this.alertMessage = alertMessage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	


}
