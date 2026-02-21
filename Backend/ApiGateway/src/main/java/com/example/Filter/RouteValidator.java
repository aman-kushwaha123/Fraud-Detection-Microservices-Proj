package com.example.Filter;

import java.net.Authenticator.RequestorType;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	// A list of API endpoints that should be publicly accessible
	// (i.e., NO authentication / NO security checks required)
	public static final List<String> openApiEndpoints = List.of(
			"/userCredential/register",
			"/userCredential/login",    // Login / token generation endpoint
			"/useCredential/validate",
			"/useCredential/isTokenexpired/**",
			"/ws",
			"/ws/**",
			"/eureka"   // Eureka service discovery endpoint
			,"/device/login/**",
			"/device/deviceCount/**"
			);
	
	// Predicate is a functional interface that returns true or false
	// This predicate checks whether a request SHOULD be secured or not
	public Predicate<ServerHttpRequest> isSecured =
              request -> openApiEndpoints
                         .stream()															// Convert the list into a stream
                         .noneMatch(uri -> request											// Check that NONE of the open endpoints match
                        		 .getURI()													//Get the full request URI
                        		 .getPath()													// Extract only the path part (e.g. /user/login)
                        		 .contains(uri));       									// Check if the path contains an open endpoint
}
