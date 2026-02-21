package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Alerts;

@Repository
public interface Alert_Repo extends JpaRepository<Alerts, Long>{
	
        Alerts findByUserId(Long userId);
}
