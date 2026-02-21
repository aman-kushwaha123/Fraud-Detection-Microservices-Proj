package com.example.Services;

import java.util.List;

import com.example.entities.Fraud_CasesRec;

public interface Fraud_CasesRec_Service {
	public List<Fraud_CasesRec> getALlRec();
	public Boolean updateStatus(Long caseId,String username);
	public void deleteFraudRecById(Long caseId);

}
