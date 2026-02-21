package com.example.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Repository.UserCredentialRepository;
import com.example.entities.UserCredential;
import com.example.entities.UserPrincipal;

@Service
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	private UserCredentialRepository userCredentialRepo;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		System.out.println("username"+username);
		
		Optional<UserCredential> user=userCredentialRepo.findByUsername(username);
		
		if(user.isPresent()) {
			return new UserPrincipal(user.get());
		}
		throw new UsernameNotFoundException("User is not found");
		
	}

}
