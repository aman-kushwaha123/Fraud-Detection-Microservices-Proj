package com.example.Filter;


import java.util.List;
import java.util.concurrent.Exchanger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.Services.JwtService;
import com.example.config.AuthConfig;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	RouteValidator routeValidator;
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@Autowired
	JwtService jwtService;
	
	
	 public AuthenticationFilter() {
		 super(Config.class);
	 }
	

	@Override
	public GatewayFilter apply(Config config) {
		/* Logic for filteration
		 * we need webflux which is required for AbstractGatewayFilter which take the input in the form of webflux
		 * 
		*/
		//we define RouteValidator which defines t he routes to whom we have to make validation
		return ((exchange,chain) -> {
				if (!routeValidator.isSecured.test(exchange.getRequest())) {
					return chain.filter(exchange);
				}
		      
				//header contains or not
			    if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
			    	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			    	return exchange.getResponse().setComplete();
			    }
			    
			    String authHeader=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			    if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			    	//actual token
			    	authHeader=authHeader.substring(7);
			    }
			    
			    try {
			    	//REST call to AUTHSERVICE OR IDENTITYSERVICE
			    	//Not RECOMMENDED because it can cause Insecurity
			    	//restTemplate.getForObject("http://IDENTITY-SERVICE//userCredential/validate?token="+authHeader, String.class);
			    	jwtService.validateToken(authHeader);	
			    	
			    	List<String> userRoles=jwtService.extractRoles(authHeader);    
			    	System.out.println("userRoles"+userRoles);
			    	boolean allowed=userRoles
			    			         .stream()
			    			         .anyMatch(config.requiredRoles::contains);
			    	if(!allowed) {
			    		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			    		return exchange.getResponse().setComplete();
			    	}
			    	
			    }
			    catch(Exception e) {
			    	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			        return exchange.getResponse().setComplete();
			    }
			    
		    
		        return chain.filter(exchange);
		});
	}
	
	public static class Config{
		private List<String> requiredRoles;
		public List<String> getRequireRoles(){
			return requiredRoles;
		}
		public void setRequiredRoles(List<String> requiredRoles) {
			this.requiredRoles=requiredRoles;		
		}

		
		
	}

}
