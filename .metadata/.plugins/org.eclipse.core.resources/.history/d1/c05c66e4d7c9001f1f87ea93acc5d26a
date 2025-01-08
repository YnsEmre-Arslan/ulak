package com.ars.user.webApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ars.user.business.abstracts.UserService;
import  com.ars.user.business.dto.UserDto;
import com.ars.user.business.request.UserRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("status") String status,
            @RequestParam("ip") String ip,
            @RequestParam("port") String port,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("username") String username,
            @RequestParam("phone") String phone,
            @RequestParam(value = "file") MultipartFile file    		
    		
    		) {
        try {
            // Kullanıcı kaydını gerçekleştir
            UserRequest userRequest = new UserRequest(name, surname, status, ip, port, email, password, username, phone, file);
            UserDto response = userService.registerUser(userRequest);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Kayıt sırasında bir hata oluştu.   "+ e.getMessage());
        }
    }
    

    
    
    
}
