package com.arst.check.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.arst.check.business.abstracts.TemplateService;
import com.arst.check.repository.MailConfigRepository;

import lombok.RequiredArgsConstructor;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

	
	private final MailConfigRepository configRepository;
	
    @Bean
    public JavaMailSender getJavaMailSender() {
    	
    	com.arst.check.entities.MailConfig  config =  configRepository.findByIdOrDefault(0);
       
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
       
    	mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getUsername());
        mailSender.setPassword(config.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", config.getSmtpAuth());
        props.put("mail.smtp.ssl.enable", config.getSslEnable());
        props.put("mail.smtp.connectiontimeout", config.getConnectionTimeout());
        props.put("mail.smtp.timeout", config.getTimeout());
        props.put("mail.smtp.writetimeout", config.getWriteTimeout());

        return mailSender;
    }
}

