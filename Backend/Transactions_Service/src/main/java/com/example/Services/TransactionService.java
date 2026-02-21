package com.example.Services;

import java.util.List;

import com.example.entities.Transactions;

public interface TransactionService {
	public void deleteTransactionById(Long id);
	public List<Transactions> allTransactions();
	public Long getTxnCountLastHour(Long userId);
	public List<Transactions> getAllTransactionsByAccId(List<Long> fromAccountIds,List<Long> toAccountIds);
	public List<Transactions> getAllTransactionsByFromAccId(List<Long> fromAccountIds);
	public List<Transactions> getAllTransactionsByToAccId(List<Long> toAccountId);
	public List<Transactions> getTransacbyUserId(Long userid);
}
