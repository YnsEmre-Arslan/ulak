package com.ars.gateway.config;



import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.ars.gateway.business.dto.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {


	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@PostConstruct
	protected void init() {

		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(UserDto userDto) {

		Date now = new Date();
		Date validity = new Date (now.getTime()+3_600_000);
		
		return JWT.create()
				.withIssuer(userDto.getLogin())//[0]
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withClaim("id", userDto.getId())
				//.withClaim("role", userDto.getRole())
				.sign(Algorithm.HMAC256(secretKey));
}
	
	   public boolean isTokenExpired(String token) {
	        try {
	            DecodedJWT decodedJWT = JWT.decode(token);
	            Date expiration = decodedJWT.getExpiresAt();
	            return expiration.before(new Date());
	        } catch (JWTVerificationException e) {
	            // Token geçersiz
	            return true;
	        }
	    }
	
	public UsernamePasswordAuthenticationToken validateToken(String token) {
		
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		DecodedJWT decoded = verifier.verify(token);
		
		
		UserDto user= UserDto.builder().
				login(null)
				.id(decoded.getClaim("id").asLong())
				//.role(decoded.getClaim("role").asString())
				.build();
		
		return new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList());
		
	}

    public UserDto decodeToken(String token) {
        
    	
    	Algorithm algorithm = Algorithm.HMAC256(secretKey);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);

        // Çözülen bilgileri kullanarak UserDto oluştur
        UserDto user = new UserDto();

        String userName[] = {decodedJWT.getIssuer()};
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        
        user.setLogin(userName[0]);;
        user.setId(id);
        //user.setRole(role);
      
        
        return user;
    }
	
    public String getUsernameFromToken(String token) {
    	    try {
            // JWT algoritması ile doğrulama
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

            // Issuer alanından username'i döndür
            return decodedJWT.getIssuer();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token doğrulama başarısız: " + e.getMessage());
        }
    }

	
	
	
}
