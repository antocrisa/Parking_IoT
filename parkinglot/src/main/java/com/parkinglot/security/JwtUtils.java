package com.parkinglot.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.parkinglot.security.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Crea e risolve il token jwt
@Component
public class JwtUtils {
 

  @Value("${parkinglot.jwtSecret}")
  private String jwtSecret;

  @Value("${parkinglot.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
	  
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
   Claims claims = Jwts.claims().setSubject((userPrincipal.getUsername()));
   		 claims.put("role", userPrincipal.getRole());		
   		 claims.put("name", userPrincipal.getName());
   		 claims.put("email", userPrincipal.getEmail());
   		 return Jwts.builder().setClaims(claims).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
    	}
  	 
  public String getUserNameFromJwtToken(String token) {
	//  System.out.println("TOKEN : "+token);
     return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }
 
  public boolean validateJwtToken(String authToken) {
    try {
    		Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch(Exception e) {
    	e.printStackTrace();
    }

    return false;
  }
}
