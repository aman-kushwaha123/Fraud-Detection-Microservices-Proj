package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class New_Device_Count {
	public Long getNew_device_count() {
		return new_device_count;
	}

	public void setNew_device_count(Long new_device_count) {
		this.new_device_count = new_device_count;
	}

	private Long new_device_count;

}
