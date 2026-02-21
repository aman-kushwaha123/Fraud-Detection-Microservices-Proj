package com.example.Services;

import java.util.List;

import com.example.entities.AccountDetails;

public interface AccountDetailsService {
	 
	public List<AccountDetails> getUserAccounts(Long userId);
	public List<AccountDetails> getAllAccountDetails();

}
