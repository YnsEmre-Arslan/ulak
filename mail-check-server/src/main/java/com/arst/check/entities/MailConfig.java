package com.arst.check.entities;

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
@Table(name = "mail_config")
public class MailConfig {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "host" , nullable = false)
    private String host;
	
	@Column(name = "port" , nullable = false)
    private int port;
	
	@Column(name = "username" , nullable = false)
    private String username;
	
	@Column(name = "password" , nullable = false)
    private String password;
	
	@Column(name = "smtp_auth" , nullable = false)
    private String smtpAuth;
	
	@Column(name = "ssl_enable" , nullable = false)
    private String sslEnable;
	
	@Column(name = "connection_timeout" , nullable = false)
    private int connectionTimeout;
	
	@Column(name = "timeout" , nullable = false)
    private int timeout;
	
	@Column(name = "write_timeout" , nullable = false)
    private int writeTimeout;

}
