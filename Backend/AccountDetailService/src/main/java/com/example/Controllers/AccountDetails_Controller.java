package com.example.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.AccountDetail_Repo;
import com.example.Services.AccountDetailsServiceImp;
import com.example.Services.KafkaProducerService;
import com.example.entities.AccountDetails;

@RestController
@RequestMapping("/Account")
public class AccountDetails_Controller {
	
	@Autowired
	private AccountDetail_Repo accountDetail_Repo;
	
	@Autowired
	private AccountDetailsServiceImp accountDetailsServiceImp;
	
	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addAccount(@RequestBody AccountDetails accountDetails){
		accountDetail_Repo.save(accountDetails);
		return ResponseEntity.ok(Map.of("message","Account Created"));
		
	}
	
	@GetMapping("/login/{user_id}")
	public ResponseEntity<?> loginAccount(@PathVariable Long user_id){
		AccountDetails dbAccount= accountDetail_Repo.findByUserId(user_id);
		System.out.println(dbAccount);
		//kafkaProducerService.sendMessage(dbAccount);
		System.out.println("Message sent Successfully from Account");
		return ResponseEntity.ok(Map.of("message",dbAccount+"Sent Successfully"));
		
	}
	@GetMapping("/allAccounts")
	public ResponseEntity<List<AccountDetails>> allAccounts(){
		List<AccountDetails> accounts=accountDetailsServiceImp.getAllAccountDetails();
		if(accounts.size() != 0) {
			return ResponseEntity.ok(accounts);
		}
		return null;
	}
	
	@GetMapping("/allAccounts/{userId}")
	public ResponseEntity<List<AccountDetails>> getAllAccounts(@PathVariable Long userId){
		List<AccountDetails> accountDetails=accountDetailsServiceImp.getUserAccounts(userId);
		try {
			if(accountDetails.size() !=0) {
				System.out.println("Successfully responsed accoutDetails to Angular");
				return ResponseEntity.ok(accountDetails);
			}
			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("No accounts are present for this user");
			
		}
	}
	
	@DeleteMapping("/delete/{accountId}")
	public ResponseEntity<?> deleteAccountById(@PathVariable Long accountId){
		accountDetail_Repo.deleteById(accountId);
		return ResponseEntity.ok(Map.of("message"," Account Deleted"));
		
	}

	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<?> deleteAllAccounts(){
		accountDetail_Repo.deleteAll();
		return ResponseEntity.ok(Map.of("message","All Account Deleted"));
		
	}
	

}
