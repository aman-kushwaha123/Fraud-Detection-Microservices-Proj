package com.example.Services;

import java.awt.RenderingHints.Key;
import java.security.KeyStore.SecretKeyEntry;
import java.security.NoSuchAlgorithmException;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.antlr.v4.runtime.misc.TestRig;
import org.hibernate.id.insert.GetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.entities.UserCredential;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	/*JWT BIG STRUCTURE
	 * HEADER.PAYLOAD.SIGNATURE
	   Claims live inside the PAYLOAD
	 */
	
	private UserCredential userCredential;
	
	private String secretKey="3dca7af538ffd55785d1253a18a0785dab955187567e913f8db9126c7ae75f1d";
	
	/*public JwtService() throws NoSuchAlgorithmException {
		//KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
		//SecretKey skey=keyGenerator.generateKey();
		//secretKey=Base64.getEncoder().encodeToString(skey.getEncoded());
		//secretKey=Base64.getEncoder().encodeToString(skey.getEncoded());
		throw new NoSuchAlgorithmException("Given algorithm is not exist");	
	}
	*/
	
	public String generateToken(String username,UserCredential userCredential) {
		// Map to hold custom claims (key-value pairs) if needed
		Map<String, Object> claims=new HashMap<String, Object>();
		
		claims.put("roles", userCredential.getRoles()
		        .stream()
		        .map(r -> "ROLE_" + r.getRoleName())
		        .toList());
		
		Date expirationDate=Date.from(Instant.now().plus(Duration.ofHours(1)));
		
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(expirationDate)
				.signWith(getKey())
				.compact(); //this will generate token for us
		
	}
	
	private Claims extractClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}
	
	
	private SecretKey getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	//Jws<Claims> represents a JWT that has been successfully verified and whose payload is accessible as Claims.
	/*Jws<Claims>
 ├── Header    (alg, typ, kid)
 ├── Claims    (sub, exp, roles, ...)
 └── Signature (verified ✔)
     we can access jws.getHeader(),jws.getPayload(),jws.getSignature()
     */
	public Jws<Claims> validateToken(final String token){
		Jws<Claims> claimsJws=Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
		return claimsJws;
	}
				                   
	
	/*public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
    
	public boolean validateToken(String token,UserDetails userDetails) {
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername()));
	}*/
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	// Generic method to extract any claim using a resolver function
	public <T> T extractClaim(String token,Function<Claims, T> claimResolver) {
		// TODO Auto-generated method stub
		 // Extract all claims from token
		final Claims claims=extractClaims(token);
		// Apply resolver function to get required claim
		return claimResolver.apply(claims);
	}
	
	
		
	
	
	

}
