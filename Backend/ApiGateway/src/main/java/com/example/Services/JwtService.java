package com.example.Services;

import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.security.RolesAllowed;

@Service
public class JwtService {
	
	private String secretKey="3dca7af538ffd55785d1253a18a0785dab955187567e913f8db9126c7ae75f1d";
	
	private SecretKey getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private Claims extractClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}
	
	public List<String> extractRoles(String token){
		 return extractClaims(token).get("roles",List.class);
	}
	
	public Jws<Claims> validateToken(final String token){
		Jws<Claims> claimsJws=Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
		return claimsJws;
	}

}
