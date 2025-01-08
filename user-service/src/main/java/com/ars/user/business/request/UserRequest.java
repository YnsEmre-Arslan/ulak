package com.ars.user.business.request;

import org.springframework.web.multipart.MultipartFile;

public record UserRequest(
        String name,
        String surname,
        String status,
        
        String ip,
        String port,
        String email,
        String password,
        
        String username,
        String phone,
        
        MultipartFile file
) {}

