package com.arst.check.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {

/*
    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

	@Bean
	public FilterRegistrationBean  corsFilter1() {
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		
		// Tüm kaynaklara izin vermek için addAllowedOriginPattern kullanabilirsiniz
		//config.addAllowedOriginPattern("*");
		
		
		config.addAllowedOrigin("*");
		//config.addAllowedOrigin("http://localhost:4200");
		config.setAllowedHeaders(Arrays.asList(
				org.springframework.http.HttpHeaders.AUTHORIZATION,
				org.springframework.http.HttpHeaders.CONTENT_TYPE,
				org.springframework.http.HttpHeaders.ACCEPT
				));
		
		config.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()
				
				
				));
		
			
		config.setMaxAge(3600L);
		
		source.registerCorsConfiguration("/**", config);
		
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(-102); 
				
		return bean;
	}


	*/
	
	  @Bean
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	    
			return http .requiresChannel(channel ->
			  channel.anyRequest().requiresSecure()) .csrf().disable()
			  .authorizeRequests(authorize -> authorize.anyRequest().permitAll()) .build();
			
		 
				
			
			 //return http.csrf(csrf -> csrf.disable()).authorizeRequests(authorize ->authorize.anyRequest().permitAll())
                             //.build();
			 
			 

			  
			 
			 
			 
			 
			 
	    }
	    
	    
}

