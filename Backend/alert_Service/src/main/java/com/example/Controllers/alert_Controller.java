package com.example.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.Alert_Repo;
import com.example.Services.AlertServiceImpl;
import com.example.entities.Alerts;

@RestController
@RequestMapping("/alert")
public class alert_Controller {
	@Autowired
	private AlertServiceImpl alertServiceImpl; 
	
	@Autowired
	private Alert_Repo alert_Repo;
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<?> getAlert(Long userId){
		Alerts alerts=alertServiceImpl.getAlerts(userId);
		return ResponseEntity.ok(alerts);
	}
	
	@GetMapping("/all/alerts")
	public ResponseEntity<?> getAlerts(){
		List<Alerts> alerts=alert_Repo.findAll();
		return ResponseEntity.ok(alerts);
	}
	
	@DeleteMapping("/delete/{alertId}")
	public ResponseEntity<?> deleteById(@PathVariable Long alertId){
		alertServiceImpl.delelteAlertById(alertId);
	    return 	ResponseEntity.ok(Map.of("message","Alert Deleted Successfully"));
	}
	
	/*
	 * only of bidirectional in Controller
	 *  @MessageMapping("/send") which client send -> /app/send
	 *  @sendTo("/topic/messages") Server broadcasts
	 *  public(ChatMessage message){
	 *  return message;
	 */
}
