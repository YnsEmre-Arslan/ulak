package com.ars.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {


	  @Bean
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	    
		  
			
			  return http .requiresChannel(channel ->
			  channel.anyRequest().requiresSecure()) .csrf().disable()
			  .authorizeRequests(authorize -> authorize.anyRequest().permitAll()) .build();
			
		 
				
			
			//return http.csrf().disable() .authorizeRequests(authorize ->
			 // authorize.anyRequest().permitAll()) .build();
 
			 
	    }
	    
	

}

