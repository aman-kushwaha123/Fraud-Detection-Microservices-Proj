package com.example.Services;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.Transaction_Repo;
import com.example.entities.Transactions;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	Transaction_Repo transaction_Repo;
	
	@Override
	public void deleteTransactionById(Long id) {
		transaction_Repo.deleteById(id);
		
	}
	
	@Override
	public List<Transactions> allTransactions(){
		List<Transactions> resultList=transaction_Repo.findAll();
		return resultList;
	}
	
	@Override
	public List<Transactions> getAllTransactionsByAccId(List<Long> fromAccountIds,List<Long> toAccountIds){
		List<Transactions> resultList=transaction_Repo.findByFromAccountIdInAndToAccountIdIn(fromAccountIds, toAccountIds);
		return resultList;
	}
	
	@Override
	public List<Transactions> getAllTransactionsByFromAccId(List<Long> fromAccountIds){
		List<Transactions> resultList=transaction_Repo.findByFromAccountIdIn(fromAccountIds);
		return resultList;
	}
	
	@Override
	public List<Transactions> getAllTransactionsByToAccId(List<Long> toAccountId){
		List<Transactions> resultList=transaction_Repo.findByToAccountIdIn(toAccountId);
		return resultList;
	}
	
	

	
	@Override
	public List<Transactions> getTransacbyUserId(Long userId){
		List<Transactions> resultList=transaction_Repo.findByUserId(userId);
		return resultList;
	}
	
	
	
	public Long getTxnCountLastHour(Long userId) {
		LocalDateTime oneHourAgoDateTime = LocalDateTime.now().minusHours(1);
		System.out.println("oneHouerAgo"+oneHourAgoDateTime);
		return transaction_Repo.countByUserIdAndTimeStamp(userId, oneHourAgoDateTime);
	}

}
