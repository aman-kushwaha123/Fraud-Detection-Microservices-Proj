package com.example.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.antlr.v4.runtime.misc.TestRig;
import org.hibernate.annotations.CreationTimestamp;

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
import jakarta.persistence.Table;

@Table
@Entity
public class Fraud_CasesRec {
	public enum Status{
		OPEN,
		CLOSED
	}
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long caseId;                        
	
	@Column
	private Long txnId;                        //fraud txn reference
	
	@Column
	private Long userId;                      //Person involved
	
	@Column
	private String reviewer;                      //Fraud team member
	
	@Enumerated(EnumType.STRING)
	private Status status=Status.OPEN;                        //OPEN/CLOSED
	
	
	@ElementCollection
	@CollectionTable(
			name="fraud_remarks",
			joinColumns= @JoinColumn(name="case_id")
			)
	@Column
	private List<String> fraudRec_remarks;							//Why it is fraud
	
	
	@Column
	@CreationTimestamp
	private LocalDateTime timeStamp;
	
	

	@Column
	private float fraudProbability=0;
	
    
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public float getFraudProbability() {
		return fraudProbability;
	}

	public void setFraudProbability(float fraudProbability) {
		this.fraudProbability = fraudProbability;
	}

	public List<String> getFraudRec_remarks() {
		return fraudRec_remarks;
	}

	public void setFraudRec_remarks(List<String> fraudRec_remarks) {
		this.fraudRec_remarks = fraudRec_remarks;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
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

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}


	
	
	

}
