package com.example.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudResponse {
	private float fraud_probability;
	
	private int isFraud;
	
	private  List<String> reasons;
	
	public float getFraud_probability() {
		return fraud_probability;
	}
	public void setFraud_probability(float fraud_probability) {
		this.fraud_probability = fraud_probability;
	}
	public int getIsFraud() {
		return isFraud;
	}
	public void setIsFraud(int isFraud) {
		this.isFraud = isFraud;
	}
	public List<String> getReasons() {
		return reasons;
	}
	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}
}
