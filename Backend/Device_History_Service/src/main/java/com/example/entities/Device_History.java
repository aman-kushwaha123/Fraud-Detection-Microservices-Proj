package com.example.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.descriptor.java.LongJavaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class Device_History {
	public enum Risk_Level{
		NORMAL,
		SUSPICIOUS
	}
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deviceId;           //device signature
	
	@Column
	private Long userId;            //Who owns device
	
	//@UpdateTimestamp   update when entity updates
	@CreationTimestamp
	@Column
	private LocalDateTime lastUsed;       //Build pattern
	

	private String visitorId;
	
	
	@Enumerated(EnumType.STRING)
	@Column( nullable = false)
	private Risk_Level riskLevel=Risk_Level.NORMAL;             //Normal/Suspicious


	public String getVisitorId() {
		return visitorId;
	}


	public void setVisitorId(String  visitorId) {
		this.visitorId = visitorId;
	}

	public Long getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public LocalDateTime getLastUsed() {
		return lastUsed;
	}


	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}


	public Risk_Level getRiskLevel() {
		return riskLevel;
	}


	public void setRiskLevel(Risk_Level riskLevel) {
		this.riskLevel = riskLevel;
	}
 
	
	
	
}
 