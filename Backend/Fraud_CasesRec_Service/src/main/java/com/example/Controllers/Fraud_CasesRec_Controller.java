package com.example.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Services.Fraud_CasesRec_ServiceImpl;
import com.example.entities.Fraud_CasesRec;
import com.example.entities.UpdateStatusDTO;

@RestController
@RequestMapping("/FraudRec")
public class Fraud_CasesRec_Controller {
	
	@Autowired
	private Fraud_CasesRec_ServiceImpl fraud_CasesRec_ServiceImpl;
	
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllFraudRec(){
		List<Fraud_CasesRec> records=fraud_CasesRec_ServiceImpl.getALlRec();
		//records.forEach(x-> System.out.println(x.getUserId()));
		return ResponseEntity.ok(records);
	}
	
	@PatchMapping("/update/status")
	public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusDTO updateStatusDTO ){
		Boolean result=fraud_CasesRec_ServiceImpl.updateStatus(updateStatusDTO.getCaseId(),updateStatusDTO.getUsername());
		if(result) {
			return ResponseEntity.ok(Map.of("message","Updated Successfully"));
		}
		return ResponseEntity.ok(Map.of("message","May be this caseId is not present"));
		
	}
	
	@DeleteMapping("/delete/{caseId}")
	public ResponseEntity<?> deleteById(@PathVariable Long caseId){
		fraud_CasesRec_ServiceImpl.deleteFraudRecById(caseId);
		return ResponseEntity.ok(Map.of("message","Successfully case deleted"));
	}
	

}
