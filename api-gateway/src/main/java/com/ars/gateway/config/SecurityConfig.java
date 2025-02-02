package com.ars.gateway.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ars.gateway.business.concretes.UserManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	private final UserAuthProvider authProvider;
	private final UserManager userManager; // UserManager'inizi ekleyin

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().requiresChannel(channel -> channel.anyRequest().requiresSecure()) // HTTPS zorunlu
				.exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
				.addFilterBefore(new JwtAuthFilter(authProvider), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request -> request
						.requestMatchers(HttpMethod.POST, "/login", "/add", "/istokenexpired", "/actuator/health",
								"/users/**", "/fallbackRoute","/check/**")
						.permitAll()
						.requestMatchers(HttpMethod.GET, "/uploads/profile-pictures/**", "/actuator/health",
								"/uploads/profile-pictures/**", "/fallbackRoute")
						.permitAll().anyRequest().authenticated())
				.cors(); // CORS yapılandırmasını burada etkinleştiriyoruz.

		return http.build();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userManager); // UserManager'ı kullanıcı doğrulama servisi
																		// olarak ayarlayın
		return authenticationManagerBuilder.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOrigin("https://ulak.arst.tr/");
		//config.addAllowedOrigin("https://ulak.arst.tr/80");
		// config.addAllowedOrigin("https://ulak.arst.tr/*");
		 config.addAllowedOrigin("https://ulak.arst.tr:8085");

		config.setAllowedHeaders(Arrays.asList(org.springframework.http.HttpHeaders.AUTHORIZATION,
				org.springframework.http.HttpHeaders.CONTENT_TYPE, org.springframework.http.HttpHeaders.ACCEPT));
		config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()));
		config.setMaxAge(3600L);

		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

//	
//	  @Bean 
//	  public WebSecurityCustomizer webSecurityCustomizer() 
//	  {
//		  return web ->
//	  web.ignoring().requestMatchers("/users/**","/uploads/profile-pictures/**"); 
//	  }
//	 

}
