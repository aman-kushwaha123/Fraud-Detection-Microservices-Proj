package com.example.Services;

import com.example.entities.Alerts;

public interface AlertService {
	public Alerts getAlerts(Long userId);
	public void delelteAlertById(Long alertId);

}
