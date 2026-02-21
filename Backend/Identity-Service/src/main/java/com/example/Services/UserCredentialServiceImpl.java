package com.example.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Repository.UserCredentialRepository;
import com.example.entities.UserCredential;
import com.example.entities.UserCredential.Status;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {
	@Autowired
	private UserCredentialRepository userCredentialRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private JwtService jwtService;

	@Override
	public void registerUser(UserCredential userCredential) {
		String encodedPassword = passwordEncoder.encode(userCredential.getPassword());
		userCredential.setPassword(encodedPassword);
		userCredentialRepo.save(userCredential);

	}
	
	@Override
	public UserCredential updateUserPassword(UserCredential userCredential) {
		UserCredential userCredential1=userCredentialRepo.findById(userCredential.getId())
				  .orElseThrow(()->new RuntimeException("User Not Found"));
		String encodedPassword = passwordEncoder.encode(userCredential.getPassword());
		  if(userCredential1!=null) {
		   userCredential1.setPassword(encodedPassword);
		   UserCredential user= userCredentialRepo.save(userCredential1);
		   return user;
		  }
		return null;

	}

	@Override
	public String getToken(String username, String password) throws UsernameNotFoundException {
		try {
			/*
			 * UsernamePasswordAuthenticationFilter ->AuthenticationManager(ProviderManager)
			 * -> DaoAuthenticationProvider
			 */
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if (authentication.isAuthenticated()) {
				Optional<UserCredential> user = userCredentialRepo.findByUsername(username);
				return jwtService.generateToken(username, user.get());
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new UsernameNotFoundException("Username not found");
		}

	}

	@Override
	public Boolean validateToken(String token) {
		if (jwtService.validateToken(token) != null) {
			return true;
		}

		return false;

	}
	
	@Override
	public void deleteUser(Long userId) {
		userCredentialRepo.deleteById(userId);
	}
	
	
	@Override
	public UserCredential  updateStatusBan(Long userId) {
	  UserCredential userCredential=userCredentialRepo.findById(userId)
			  .orElseThrow(()->new RuntimeException("User Not Found"));
	  if(userCredential!=null) {
	    userCredential.setStatus(Status.BANNED);
	    UserCredential user=userCredentialRepo.save(userCredential);
	    return user;
	  }
	  return null;
		
	}
	
	@Override
	public UserCredential updateStatusUnBan(Long userId) {
		  UserCredential userCredential=userCredentialRepo.findById(userId)
				  .orElseThrow(()->new RuntimeException("User Not Found"));
		  if(userCredential!=null) {
		    userCredential.setStatus(Status.UNBANNED);
		   UserCredential user= userCredentialRepo.save(userCredential);
		  return user;
		  }
		  return null;
			
		}
	
	@Override
	public UserCredential updateUserCredential(UserCredential userCredential) {
		  UserCredential userCredential1=userCredentialRepo.findById(userCredential.getId())
				  .orElseThrow(()->new RuntimeException("User Not Found"));
		  if(userCredential1!=null) {
		    userCredential1.setUsername(userCredential.getUsername());
		    userCredential1.setEmail(userCredential.getEmail());
		    userCredential1.setMobileNo(userCredential.getMobileNo());
		    UserCredential user= userCredentialRepo.save(userCredential1);
		    return user;
		  }
		  return null;
			
		}
	
	

}
