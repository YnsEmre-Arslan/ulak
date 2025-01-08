package com.ars.gateway.business.request;

import org.springframework.web.multipart.MultipartFile;

public record RegisterRequest(
		
		String name,
		String surname,
        String status,
        
        
    	String ip,
		String port,
        String email,
        String password,
        
        String username,
        String phone,
        
        MultipartFile file) {

}
