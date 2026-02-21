package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.Alert_Repo;
import com.example.entities.Alerts;
import com.example.entities.FraudResponse;
import com.example.entities.ML_Response;

@Service
public class AlertServiceImpl implements AlertService{
      
	@Autowired
	private Alert_Repo alert_Repo;
	
	@Override
	public Alerts getAlerts(Long userId) {
		return alert_Repo.findByUserId(userId);
	}
	
	@Override
	public void delelteAlertById(Long alertId) {
		 alert_Repo.deleteById(alertId);
	}
	
	
	
}
