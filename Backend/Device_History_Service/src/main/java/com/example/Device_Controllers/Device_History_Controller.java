package com.example.Device_Controllers;

import java.lang.module.FindException;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.Device_History_Repo;
import com.example.Services.KafkaProducerService;
import com.example.entities.Device_History;
import com.example.entities.Device_History.Risk_Level;
import com.example.entities.New_Device_Count;

@RestController
@RequestMapping("/device")
public class Device_History_Controller {
	
	@Autowired
	Device_History_Repo device_History_Repo;
	
	@Autowired
	KafkaProducerService kafkaProducerService;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginDevice(@RequestBody Device_History device_Hist) {
		
		Device_History device=device_History_Repo.findByUserIdAndVisitorId(device_Hist.getUserId(),device_Hist.getVisitorId());
		
        if(device!=null && device.getVisitorId().equals(device_Hist.getVisitorId())) {
		device.setLastUsed(LocalDateTime.now());
		device_History_Repo.save(device);
		System.out.println("device id"+device.getUserId());
		//Device_History device_History=device_History_Repo.findByUserIdAndDeviceId(device.getUserId(),device.getDeviceId());
		System.out.println("Message sent Successfully from Device");
		return ResponseEntity.ok(Map.of("device",device));
        }
		Device_History resultDevice_History= device_History_Repo.save(device_Hist);
		System.out.println("Device added");
		return ResponseEntity.ok(Map.of("device",resultDevice_History));
		
	    
	}
	
	@GetMapping("/deviceCount/{userId}")
	public ResponseEntity<?> getdeviceCount(@PathVariable Long userId) {
		System.out.println(userId);
		Long countLong=device_History_Repo.countByUserId(userId);
		New_Device_Count new_Device_Count=new New_Device_Count();
		new_Device_Count.setNew_device_count(countLong);
		kafkaProducerService.sendMessage2(new_Device_Count);
		//kafkaProducerService.sendMessage(device_History);
		if(countLong>=2) {
			List<Device_History> device_History=device_History_Repo.findByUserId(userId);
			device_History.forEach(item->item.setRiskLevel(Risk_Level.SUSPICIOUS));
			device_History_Repo.saveAll(device_History);
			System.out.println("devicecount1"+countLong);
			return ResponseEntity.ok(countLong);
		}
		return ResponseEntity.ok(Map.of("message","Successfully sended device count"));
	}
	
	@GetMapping("/all/devices")
	public ResponseEntity<?> getAllDevices(){
	    List<Device_History> device_History= device_History_Repo.findAll();
		return ResponseEntity.ok(device_History);
		
	}
	
	
	
	@DeleteMapping("/delete/{deviceId}")
	public ResponseEntity<?> deleteDeviceById(@PathVariable Long deviceId){
		device_History_Repo.deleteById(deviceId);
		return ResponseEntity.ok(Map.of("message","Device is Deleted Sucessfully"));
		
	}
	
	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<?> deleteAllDevice(){
		device_History_Repo.deleteAll();
		return ResponseEntity.ok(Map.of("message","All Devices are Deleted Sucessfully"));
		
	}
	
	
	

}
