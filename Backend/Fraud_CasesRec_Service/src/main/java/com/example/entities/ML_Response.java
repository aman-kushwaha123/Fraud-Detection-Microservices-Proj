package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ML_Response {
	private Long txnId;					//Link alert to txn
		
	private Long userId;					//Who received alert Notification
		
		
	private FraudResponse fraudResponse;


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


	public FraudResponse getFraudResponse() {
		return fraudResponse;
	}


	public void setFraudResponse(FraudResponse fraudResponse) {
		this.fraudResponse = fraudResponse;
	}
	
	
	
}
