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
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@PostConstruct
	protected void init() {

		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(UserDto userDto) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000); // 1 hour
        
        System.out.println("Current Time: " + now);
        System.out.println("Expiration Time: " + validity);
        
		return JWT.create()
				.withIssuer(userDto.getFirstName())
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withClaim("id", userDto.getId())
				.withClaim("firstName", userDto.getFirstName())
				.withClaim("lastName", userDto.getLastName())
				.sign(Algorithm.HMAC256(secretKey));
}
	
	
	public UsernamePasswordAuthenticationToken validateToken(String token) {
		
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		DecodedJWT decoded = verifier.verify(token);
		
		
		UserDto user= UserDto.builder().
				login(null)
				.id(decoded.getClaim("id").asLong())
				.lastName(decoded.getClaim("role").asString())
				.build();
		
		return new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList());
		
	}
	
	
	
    public UserDto decodeToken(String token) {
        //Tekrar bakılacak
    	
    	Algorithm algorithm = Algorithm.HMAC256(secretKey);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);

        // Çözülen bilgileri kullanarak UserDto oluştur
        UserDto user = new UserDto();

        String userName[] = {decodedJWT.getIssuer()};
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        
        user.setFirstName("");;
        user.setId(id);

      
        
        return user;
    }
	
	
	
	
}

