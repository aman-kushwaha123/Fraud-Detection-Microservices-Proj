package com.example.Services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.entities.Device_History;
import com.example.entities.New_Device_Count;

@Service
public class KafkaProducerService {
	private KafkaTemplate<String,Object> kafkaTemplate;
	
	
	public KafkaProducerService(KafkaTemplate<String,Object> kafkaTemplate) {
		this.kafkaTemplate=kafkaTemplate;
		
	}
	
	/*public void sendMessage(Device_History device_History) {
		kafkaTemplate.send("Device-topic", device_History);
		System.out.println("Sucessfully sent Message");
		
	}*/
	public void sendMessage2(New_Device_Count new_device_count) {
		kafkaTemplate.send("NewDeviceCount-topic",new_device_count);		
	}

}
