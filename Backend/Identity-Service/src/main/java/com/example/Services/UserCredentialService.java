package com.example.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.entities.UserCredential;

@Service
public interface UserCredentialService {
	
	public void registerUser(UserCredential userCredential);
	public UserCredential updateUserPassword(UserCredential userCredential);
	public String getToken(String username,String password);
	public Boolean validateToken(String token);
	public void deleteUser(Long userId);
	public UserCredential updateStatusBan(Long userId);
	public UserCredential updateStatusUnBan(Long userId);
	public UserCredential updateUserCredential(UserCredential userCredential) ;

}
