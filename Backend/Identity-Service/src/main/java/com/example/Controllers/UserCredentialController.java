package com.example.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.UserCredentialDTO;
import com.example.Repository.RoleRepository;
import com.example.Repository.UserCredentialRepository;
import com.example.Services.JwtService;
import com.example.Services.UserCredentialServiceImpl;
import com.example.Services.UserDetailService;
import com.example.entities.Role;
import com.example.entities.Role.RoleName;
import com.example.entities.UserCredential;

@RestController
@RequestMapping("/userCredential")
public class UserCredentialController {
	@Autowired
	private UserCredentialServiceImpl userCredentialServiceImpl;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserCredentialRepository userCredentialRepository;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody UserCredential userCredential){
		if(userCredential !=null) {
		Set<Role> role=new HashSet<Role>();
		role.add(roleRepository.findByRoleName(RoleName.USER));
		System.out.println(role);
		userCredential.setRoles(role);
		userCredentialServiceImpl.registerUser(userCredential);
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","User Registered Successfully"));
		}
		return ResponseEntity.ok(Map.of("message","UserCreditial is not exist"));
		
	}
	
	@PatchMapping("/role/setAdmin/{username}")
	public ResponseEntity<?> UpdateRole(@PathVariable String username) {
		UserCredential user=userCredentialRepository.findByUsername(username)
				.orElseThrow(()->new RuntimeException("user not found"));
		Set<Role> r=user.getRoles();
		r.forEach(role ->
		         System.out.println(role.getRoleName())
		  );
		Role role=roleRepository.findByRoleName(RoleName.ADMIN);     //we are updating set<Role> that why we use Role type role
		user.getRoles().clear(); 
		user.getRoles().add(role);   //(Industry standard) Modify the existing collection,don't replace it that's why add()/remove()
		userCredentialRepository.save(user);
		return ResponseEntity.ok(Map.of("message","Successfully Updated to Admin"));
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody UserCredentialDTO userCredentialDTO){
        if(userCredentialDTO!=null) {
        try {
        	String username=userCredentialDTO.getUsername();
    		String password=userCredentialDTO.getPassword();
    		String token=userCredentialServiceImpl.getToken(username, password);
    		UserCredential userCredential=userCredentialRepository.findByUsername(username)
    				.orElseThrow(()->new RuntimeException("User not found"));
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token",token,"user",userCredential));
        }
        catch (Exception e) {
			// TODO: handle exception
        	throw new RuntimeException("invalid access");
		}

        }
        
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Something Went Wrong Usercredeintial might be null"));
		
	}
	
	@GetMapping("/validate")
	public ResponseEntity<?> validateToken(@RequestParam String token){
		userCredentialServiceImpl.validateToken(token);
		return ResponseEntity.ok(Map.of("message","Token is Valid"));
		
	}
	
	@GetMapping("/isTokenexpired/{token}")
	public ResponseEntity<?> isTokenExpired(@PathVariable String token){
		Boolean result=jwtService.isTokenExpired(token);
		return ResponseEntity.ok(Map.of("isTokenExpired",result));
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteByUserId(@PathVariable Long userId){
		userCredentialServiceImpl.deleteUser(userId);
		return ResponseEntity.ok(Map.of("message","User Deleted Successfully"));
	}
	
	
	@PatchMapping("/update/status/ban/{userId}")
	public ResponseEntity<?> updateStatusBanById(@PathVariable Long userId){
		UserCredential userCredential= userCredentialServiceImpl.updateStatusBan(userId);
		if(userCredential!=null ){
		return ResponseEntity.ok(Map.of("user",userCredential));
		}
		return ResponseEntity.ok("Something Went Wrong");
	}
	
	@PatchMapping("/update/status/unban/{userId}")
	public ResponseEntity<?> updateStatusUnBanById(@PathVariable Long userId){
		UserCredential user= userCredentialServiceImpl.updateStatusUnBan(userId);
		if(user!=null) {
		return ResponseEntity.ok(Map.of("user",user));
		}
		return ResponseEntity.ok("Something Went Wrong");
	}
	
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<?> getByUserId(@PathVariable Long userId){
		UserCredential userCredential=this.userCredentialRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User not Found"));
		if(userCredential!=null) {
			return ResponseEntity.ok(userCredential);
		}
		return ResponseEntity.ok("User not Found");
		
	}
	
	
	@GetMapping("/all/users")
	public ResponseEntity<?> getAllUsers(){
		List<UserCredential> userCredentials=userCredentialRepository.findAll();
		return ResponseEntity.ok(userCredentials);
	}
	
	
	@PatchMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserCredential userCredential){
		  UserCredential result=userCredentialServiceImpl.updateUserCredential(userCredential);
		  if(result!=null) {
			  return ResponseEntity.ok(Map.of("user",result));
		  }
		  return ResponseEntity.ok(Map.of("message","User Not found Result is null"));
		
	}
	
	@PatchMapping("/update/password")
	public ResponseEntity<?> updateUserPassword(@RequestBody UserCredential userCredential){
		  UserCredential result=userCredentialServiceImpl.updateUserPassword(userCredential);
		  if(result!=null) {
			  return ResponseEntity.ok(Map.of("user","Successfully updated user Password"));
		  }
		  return ResponseEntity.ok(Map.of("message","User Not found Result is null"));
		
	}
		
	}
