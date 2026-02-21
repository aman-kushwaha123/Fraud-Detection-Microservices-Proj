package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device_History {
	public enum Risk_Level{
		Normal,
		Suspicious
	}
	
	
	private Long deviceId;           //device signature
	
	
	private Long userId;            //Who owns device
	
	
	
	private Risk_Level riskLevel=Risk_Level.Normal;             //Normal/Suspicious


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


	

	public Risk_Level getRiskLevel() {
		return riskLevel;
	}


	public void setRiskLevel(Risk_Level riskLevel) {
		this.riskLevel = riskLevel;
	}
 
	

}
