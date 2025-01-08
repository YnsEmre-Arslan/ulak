package com.ars.message.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "mail_server_config")
public class MailServerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "host" , nullable = false)
    private String host;
	
	@Column(name = "port" , nullable = false)
    private String port;
	
	@Column(name = "username" , nullable = false)
    private String username;
	
	@Column(name = "password" , nullable = false)
    private String password;
	
	@Column(name = "protocol" , nullable = false)
    private String protocol; // IMAP, POP3 veya SMTP

	@Column(name = "mail_user" , nullable = false)
    private String mailUser; 
}

