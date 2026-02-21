package com.example.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudRequest {
	private Double amount_ratio;
	
	private Long new_device;
	
	private Long txn_count_hr;
	
	public Double getAmount_ratio() {
		return amount_ratio;
	}
	public void setAmount_ratio(Double amount_ratio) {
		this.amount_ratio = amount_ratio;
	}
	public Long getNew_device() {
		return new_device;
	}
	public void setNew_device(Long new_device) {
		this.new_device = new_device;
	}
	public Long getTxn_count_hr() {
		return txn_count_hr;
	}
	public void setTxn_count_hr(Long txn_count_hr) {
		this.txn_count_hr = txn_count_hr;
	}
	
	
	

}
