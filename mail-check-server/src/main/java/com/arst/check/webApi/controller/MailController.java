package com.arst.check.webApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.arst.check.business.abstracts.EmailService;
import com.arst.check.business.request.LoginCheckRequest;
import com.arst.check.business.request.SupportRequest;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class MailController {

    
    private final EmailService mailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody LoginCheckRequest emailRequest) {
        try {
            mailService.checkEmail(emailRequest);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
    
    
    @PostMapping("/send/support")
    public ResponseEntity<String> sendSupport(@RequestBody SupportRequest supportRequest) {
        try {
            mailService.sendSupportEmail(supportRequest);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
}


