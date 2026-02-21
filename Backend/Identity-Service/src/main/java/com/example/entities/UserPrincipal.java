package com.example.entities;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



//says current user
public  class UserPrincipal implements UserDetails{
	private UserCredential userCredential;
	
	public UserPrincipal(UserCredential userCredential) {
		this.userCredential=userCredential;
	}
	
	/* Why this return type
	 * Spring Security internally works with GrandtedAuthority,not roles directly
	 * GrantedAuthorty -> a permission/authority
	 * SimpleGrantedAuthority -> implementation of GrantedAuthority
	 * So Spring Security wants "Give me a collection of any object that extends GrantedAuthority
	 * Spring Security Expects this format ROLE_<ROLE_NAME>
	 * (.collect(Collectors.toList()); 
	 * coverts Stream<SimpleGrantedAuthority> -> List<SimpleGrantedAuthority>
	 * Collection -> ex.List,Set
	 * Collector(is a technique) -> toList(),groupingBy()
	 */
	
	@Override
   public Collection<? extends GrantedAuthority> getAuthorities()                     //This is Method Signature 
	{
		System.out.println(userCredential.getRoles());
		return userCredential.getRoles()
				.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
				.collect(Collectors.toList());
	}
	
	//roles
	@Override
	public String getPassword() {
		return userCredential.getPassword();
	}

	@Override
	public String getUsername() {
		return userCredential.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
