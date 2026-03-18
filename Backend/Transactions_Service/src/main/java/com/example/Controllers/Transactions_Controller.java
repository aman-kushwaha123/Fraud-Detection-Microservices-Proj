package com.example.Controllers;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.Repository.Transaction_Repo;
import com.example.Services.KafkaConsumerService;
import com.example.Services.KafkaProducerService;
import com.example.Services.TransactionServiceImpl;
import com.example.entities.AccountDetails;
import com.example.entities.Transactions;

import com.example.entities.Transactions.Status;
import com.example.entities.Txn_Count_1Hr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/transaction")
public class Transactions_Controller {
	
	@Autowired
	Transaction_Repo transaction_Repo;
	
	@Autowired
	KafkaProducerService kafkaProducerService;
	
	@Autowired
	KafkaConsumerService kafkaConsumerService;
	
	@Autowired
	TransactionServiceImpl transactionServiceImpl;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@PostMapping("/add")
	public ResponseEntity<?> add_transaction(@RequestBody Transactions transactions) throws JsonProcessingException  {
		   
		   Transactions transaction1= transaction_Repo.save(transactions);
		   //AccountDetails Account=objectMapper.readValue(transaction1.getAccount(), AccountDetails.class);
		   kafkaProducerService.sendMessage(transaction1);	   
		   
		   System.out.println("UserId"+transaction1.getUserId());
		   Long count =transactionServiceImpl.getTxnCountLastHour(transaction1.getUserId());
		   System.out.println("Txn_count_1Hr"+count);
		   
		   Txn_Count_1Hr txn_Count_1Hr=new Txn_Count_1Hr();
		   txn_Count_1Hr.setTxn_count_1hr(count);
		   kafkaProducerService.SendTxn_Count_1Hr(txn_Count_1Hr);
		   System.out.println("Txn_Count_1Hr"+count);
		   System.out.println(kafkaConsumerService.getIsFraud());
		   /*if(kafkaConsumerService.getIsFraud() == false) {
			   Transactions updateTransactions=transaction_Repo.findById(transaction1.getTxnId())
					   .orElseThrow(()->new RuntimeException("UpdateTransaction Not Found"));
			   updateTransactions.setStatus(Status.SUCCESS);
			   transaction_Repo.save(updateTransactions);
			   System.out.println("Successfully Updated Transaction status");
		   return ResponseEntity
				   .ok(Map.of("message","Transaction Added in Db and Message Sent Successfully","is_Fraud",kafkaConsumerService.getIsFraud()));
		   }*/
	       return ResponseEntity.ok(Map.of("message","Transaction Created","is_Fraud",kafkaConsumerService.getIsFraud()));
	}
	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<?> deleteAllTransaction(){
		transaction_Repo.deleteAll();
		return ResponseEntity.ok(Map.of("message","Sucessfully Deleted All Transactions"));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete_transaction(@PathVariable Long id) {
		transactionServiceImpl.deleteTransactionById(id);
		return ResponseEntity.ok(Map.of("message","Successfully Deleted"));
	}

	
	@GetMapping("/all/transactions")
	public ResponseEntity<?> allTransactions() {
		List<Transactions> resultList=transactionServiceImpl.allTransactions();
		return ResponseEntity.ok(resultList);
	}
	
	@GetMapping("/all/transactions/{fromAccountIds}/{toAccountIds}")
	public ResponseEntity<?> getTransactions(@PathVariable List<Long> fromAccountIds,@PathVariable(required = false) List<Long>  toAccountIds ) {
			List<Transactions> resultList1=transactionServiceImpl.getAllTransactionsByAccId(fromAccountIds,toAccountIds);
			List<Transactions>  resultList2=transactionServiceImpl.getAllTransactionsByFromAccId(fromAccountIds) ; // debited
			List<Transactions> resultList3=transactionServiceImpl.getAllTransactionsByToAccId(toAccountIds);   // credited
			List<Transactions> combinedList = new ArrayList<>();
			if (resultList2 != null) combinedList.addAll(resultList2);
			if (resultList3 != null) combinedList.addAll(resultList3);

			return ResponseEntity.ok(combinedList);
		   
		   

	}
	
	
	
	

}
