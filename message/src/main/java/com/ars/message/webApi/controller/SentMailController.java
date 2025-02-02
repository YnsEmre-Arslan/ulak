package com.ars.message.webApi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ars.message.business.abstracts.SentMailService;
import com.ars.message.business.request.DeleteMessageRequest;
import com.ars.message.business.request.SendEmailRequest;
import com.ars.message.business.request.UpdateMessageRequest;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor; 

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class SentMailController {
	
	
	private final SentMailService mailService;
	
    @PostMapping("/send")
    public String sendEmail(@RequestBody SendEmailRequest emailRequest) {
        try {
        	mailService.sendEmail(emailRequest);
            return "E-posta başarıyla gönderildi!";
        } catch (MessagingException e) {
            return "E-posta gönderimi sırasında hata oluştu: " + e.getMessage();
        }
    }
    
    
    @PostMapping("/delete")
    public ResponseEntity<?> deleteMail( @RequestBody DeleteMessageRequest deleteMessageRequest) {
        try {

        	
            // Mail okuma işlemini başlat
              mailService.deleteEmail(deleteMessageRequest);
            
            return ResponseEntity.ok("Ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error delete mails: " + e.getMessage());
        }
    }
    
    
    @PostMapping("/update")
    public ResponseEntity<?> updateMail( @RequestBody UpdateMessageRequest updateMessageRequest) {
        try {

    		
            // Mail okuma işlemini başlat
             String newId =  mailService.updateEmail(updateMessageRequest);
            
            return ResponseEntity.ok(newId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error update mails: " + e.getMessage());
        }
    }

}
 