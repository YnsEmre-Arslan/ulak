package com.ars.user.business.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MailServerConfig {
    
	    
    private String host;
	
    private String port;
	
    private String username;
	
    private String password;
	
    private String protocol; // IMAP, POP3 veya SMTP

    private String mailUser; 
}

