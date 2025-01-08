package com.ars.user.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
    
	/*
	 * private static final Long MAX_AGE = 3600L; private static final int
	 * CORS_FILTER_ORDER = -102;
	 * 
	 * @Bean public FilterRegistrationBean corsFilter1() {
	 * 
	 * UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
	 * CorsConfiguration();
	 * 
	 * config.setAllowCredentials(true);
	 * 
	 * // Tüm kaynaklara izin vermek için addAllowedOriginPattern kullanabilirsiniz
	 * //config.addAllowedOriginPattern("*");
	 * 
	 * 
	 * config.addAllowedOrigin("http://localhost:4200");
	 * config.setAllowedHeaders(Arrays.asList(
	 * org.springframework.http.HttpHeaders.AUTHORIZATION,
	 * org.springframework.http.HttpHeaders.CONTENT_TYPE,
	 * org.springframework.http.HttpHeaders.ACCEPT ));
	 * 
	 * config.setAllowedMethods(Arrays.asList( HttpMethod.GET.name(),
	 * HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()
	 * 
	 * 
	 * ));
	 * 
	 * 
	 * config.setMaxAge(3600L);
	 * 
	 * source.registerCorsConfiguration("/**", config);
	 * 
	 * FilterRegistrationBean bean = new FilterRegistrationBean(new
	 * CorsFilter(source)); bean.setOrder(-102);
	 * 
	 * return bean; }
	 * 
	 *  */
	  @Bean 
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  
	  
	  
	  return http .csrf().disable() // CSRF korumasını devre dışı bırak
	  .requiresChannel(channel -> channel.anyRequest().requiresSecure())
	  .authorizeRequests(authorize -> authorize.anyRequest().permitAll()) .build();
	  
	  
	  
	 // return http.csrf().disable() .authorizeRequests(authorize ->
	// authorize.anyRequest().permitAll()) .build();
	  
	  }
	
	    
}
