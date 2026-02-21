package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Fraud_CasesRec;

@Repository
public interface Fraud_CasesRec_Repo extends JpaRepository<Fraud_CasesRec, Long>{

}
