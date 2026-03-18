package com.example.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.Repository.Alert_Repo;
import com.example.entities.Alerts;
import com.example.entities.Alerts.Status;
import com.example.entities.FraudResponse;
import com.example.entities.ML_Response;

@Service
public class KafkaConsumerService {
	
	    @Autowired
	    private Alert_Repo alert_Repo;
	    
	    private final SimpMessagingTemplate messagingTemplate;
	    
	    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate) {
	    	this.messagingTemplate=messagingTemplate;
	    }
	    
	    
	    @KafkaListener(topics = "MLResponse-topic", groupId = "alertConsumer-Group")
	    @Async
	    public void consumeMLResponse(ML_Response ml_Response) {
	         handleMessage(ml_Response);
	    }

	    private void handleMessage(ML_Response ml_Response) {
	    	System.out.println("Successfully received ML_Response in Alert Service"+ml_Response);
	    	System.out.println(ml_Response.getUserId());
	    	System.out.println("UserId "+ml_Response.getUserId());
	    	if(ml_Response.getFraudResponse().getIsFraud() !=0) {
	    		Alerts alerts = new Alerts();
		        alerts.setTxnId(ml_Response.getTxnId());
		        alerts.setUserId(ml_Response.getUserId());
		        alerts.setAlertMessage(ml_Response.getFraudResponse().getReasons());
		        alerts.setStatus(Status.SENT);
		        alert_Repo.save(alerts);
		        messagingTemplate.convertAndSend("/topic/alert."+ml_Response.getUserId(), alerts);
	    	}
	        
	    }

	    
	   
	
	

}
