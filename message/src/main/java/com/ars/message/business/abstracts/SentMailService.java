package com.ars.message.business.abstracts;

import java.io.IOException;

import com.ars.message.business.request.DeleteMessageRequest;
import com.ars.message.business.request.SendEmailRequest;
import com.ars.message.business.request.UpdateMessageRequest;

import jakarta.mail.MessagingException;

public interface SentMailService {
    public void sendEmail(SendEmailRequest emailRequest) throws MessagingException;
    
    public void deleteEmail(DeleteMessageRequest deleteMessageRequest) throws MessagingException;
    
    
    public String updateEmail(UpdateMessageRequest updateMessageRequest) throws MessagingException, IOException;
    
 
}
