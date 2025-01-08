package com.ars.gateway.webApi.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ars.gateway.business.abstracts.UserService;
import com.ars.gateway.business.dto.CredentialsDto;
import com.ars.gateway.business.dto.StatusDto;
import com.ars.gateway.business.dto.TokenExpired;
import com.ars.gateway.business.dto.UserDto;
import com.ars.gateway.business.request.DeleteRequest;
import com.ars.gateway.business.request.TokenRequest;
import com.ars.gateway.business.request.UserAdd;
import com.ars.gateway.config.UserAuthenticationProvider;
import com.ars.gateway.entities.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
	
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody  CredentialsDto credentialsDto ) 
	{
		  	UserDto userDto = userService.login(credentialsDto);
	                	        
	        return ResponseEntity.ok(userDto);
	}
	
	@PostMapping("/istokenexpired")
	public ResponseEntity<StatusDto> tokenExpired(@RequestBody  TokenExpired tokenExpired) 
	{	
			String actualToken = tokenExpired.token().startsWith("Bearer ") ? tokenExpired.token().substring(7) : tokenExpired.token();
		  	Boolean tokenStatus = userAuthenticationProvider.isTokenExpired(actualToken);
		  	
		  	StatusDto statusDto = new StatusDto();
		  	
		  	statusDto.setStatus(tokenStatus);
		  	
	        return ResponseEntity.ok(statusDto);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addFirms(@RequestBody UserAdd userAdd ) 
	{
		try {
			UserDto userDto = userService.add(userAdd);
			return ResponseEntity.ok(userDto);
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body("İstek geçersiz");

		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody  DeleteRequest userDelete) 
	{
		
		try {
			userService.delete(userDelete.id());
			return ResponseEntity.ok("İşlem Başarılı");
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body("İstek geçersiz");

		}
        
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<User>> getAll() 
	{
		List<User> firms =userService.getAll();
	        	        
	        return ResponseEntity.ok(firms);
	}
	
	@PostMapping("/username")
	public ResponseEntity<?> getUsername(@RequestBody TokenRequest tokenRequest) 
	{
	              
			String username = userService.getUsernameFromToken(tokenRequest.token());
		
	        return ResponseEntity.ok(username);
	}

	
}
