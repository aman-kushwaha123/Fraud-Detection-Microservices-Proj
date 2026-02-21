package com.example.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.Fraud_CasesRec_Repo;
import com.example.entities.Fraud_CasesRec;
import com.example.entities.Fraud_CasesRec.Status;

@Service
public class Fraud_CasesRec_ServiceImpl implements Fraud_CasesRec_Service{
	
	@Autowired
	private Fraud_CasesRec_Repo fraud_CasesRec_Repo;
	
	@Override
	public List<Fraud_CasesRec> getALlRec() {
		List<Fraud_CasesRec> records=fraud_CasesRec_Repo.findAll();
		return records;
	}
	
	@Override
	public void deleteFraudRecById(Long caseId) {
		fraud_CasesRec_Repo.deleteById(caseId);
	}

	@Override
	public Boolean updateStatus(Long caseId,String username) {
		Fraud_CasesRec fraud_CasesRec=fraud_CasesRec_Repo.findById(caseId)
				.orElseThrow(()->new RuntimeException("No Fraud Records"));
		if(fraud_CasesRec!=null) {
			fraud_CasesRec.setReviewer(username);
			fraud_CasesRec.setStatus(Status.CLOSED);
			fraud_CasesRec_Repo.save(fraud_CasesRec);
			return true;
		}
		return false;
		
	}
}
