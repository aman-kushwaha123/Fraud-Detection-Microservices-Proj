package com.example.Config;

import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;

import com.example.Services.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailService userDetailService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//By defaultl spring security works with securityFilterchain(where needed login form,etc) now we our custom securityfilterchain 
	//so that it does not ask credentials for our /register,/validate,/token apis
	/*
	 * Turns OFF HTTP Basic authentication
			So:
		❌ No browser popup
		❌ No Authorization: Basic auth
		❌ No default Spring Security login behavior
		
		.httpBasic(Customizer.withDefaults())
			-This configures HTTP Basic authentication (username + password in headers).
			-Good use cases Internal APIs,Simple admin tools,Testing / learning,Non-browser clients (Postman, curl)
		Bad use cases JWT-based authentication,Mobile apps,Production public APIs,Anything requiring stateless tokens
		
	 */
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		return http.csrf(csrf -> csrf.disable())
		         .authorizeHttpRequests(request -> request
				 .requestMatchers("/userCredential/**").permitAll()
		         .anyRequest().authenticated())
		         .httpBasic(customizer -> customizer.disable())		//we can name any name in place of customizer
		         .formLogin(form -> form.disable())			//Turns OFF form-based login
		         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		         .build();
		
        
	}
	
	
	//Here we are using DAOAuthenticationProvider
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}

	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http)throws Exception{
		AuthenticationManagerBuilder builder=http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.authenticationProvider(authenticationProvider());
		return builder.build();
		
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return (request,response,exception) ->{
		     response.setStatus(HttpStatus.UNAUTHORIZED.value());
		};
	}
	
	

}
