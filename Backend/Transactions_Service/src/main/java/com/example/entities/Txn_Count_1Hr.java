package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Txn_Count_1Hr {
	private Long txn_count_1hr;

	public Long getTxn_count_1hr() {
		return txn_count_1hr;
	}

	public void setTxn_count_1hr(Long txn_count_1hr) {
		this.txn_count_1hr = txn_count_1hr;
	}
	
	


}
