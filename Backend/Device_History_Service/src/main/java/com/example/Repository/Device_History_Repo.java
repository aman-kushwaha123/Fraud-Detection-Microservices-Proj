package com.example.Repository;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Device_History;

@Repository
public interface Device_History_Repo extends JpaRepository<Device_History, Long>{
	Device_History findByUserIdAndDeviceId(Long userId,Long deviceId);
	
	@Query(
			value="SELECT COUNT(*) FROM device_history WHERE user_id = :userId",
			nativeQuery=true
			)
	Long countByUserId(@Param("userId") Long userId);
	
	List<Device_History> findByUserId(Long userId);
	
	Device_History findByUserIdAndVisitorId(Long userId,String visitorId);
	
	//filters devices by userId ,Orders by deviceId descending ,Returs the first one i.e,latest device
	Device_History findTopByUserIdOrderByDeviceIdDesc(Long userId);

}
