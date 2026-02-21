package com.example.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.AccountDetail_Repo;
import com.example.entities.AccountDetails;

@Service
public class AccountDetailsServiceImp implements AccountDetailsService{
	
	@Autowired
	private AccountDetail_Repo accountDetail_Repo;
	 
	@Override
	public List<AccountDetails> getUserAccounts(Long userId) {
		return accountDetail_Repo.findAllByUserId(userId);
	}
	
	@Override
	public List<AccountDetails> getAllAccountDetails(){
		return accountDetail_Repo.findAll();
	}

}
