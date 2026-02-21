package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long>{

	Optional<UserCredential> findByUsername(String username);
	
	

}
