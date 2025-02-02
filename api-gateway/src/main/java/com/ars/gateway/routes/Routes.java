package com.ars.gateway.routes;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

import java.net.URI;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

	
	@Bean
	public RouterFunction<ServerResponse> mailServiceRoute()
	{
		
		return GatewayRouterFunctions
				.route("mail_service")
				.route(RequestPredicates.path("/check/send"), 
						HandlerFunctions.http("https://ulak.arst.tr:8085"))

				.build();
		
	}
	


	@Bean
	public RouterFunction<ServerResponse> userServiceRoute()
	{
		
		return GatewayRouterFunctions
				.route("user_service")
				.route(RequestPredicates.path("/users/**"), HandlerFunctions.http("https://ulak.arst.tr:8086"))
				.build();
		
	}
	
	
	@Bean
	public RouterFunction<ServerResponse> messageServiceRoute()
	{
		
		return GatewayRouterFunctions
				.route("message_service")
				.route(RequestPredicates.path("/mail/**"), HandlerFunctions.http("https://ulak.arst.tr:8087"))
				.build();
		
	}
	
	
	
	
	@Bean
	public RouterFunction<ServerResponse> imageServiceRoute()
	{
		
		return GatewayRouterFunctions
				.route("user_service")
				.route(RequestPredicates.path("/uploads/profile-pictures/**"), HandlerFunctions.http("https://ulak.arst.tr:8086"))
				.filter(CircuitBreakerFilterFunctions.circuitBreaker("imageServiceCircuitBreaker",
						URI.create("forward:/fallbackRoute")))
				.build();
		
	}


	

	
	
	/*
	 * @Bean public RouterFunction<ServerResponse> orderServiceRoute() {
	 * 
	 * return GatewayRouterFunctions .route("order_service")
	 * .route(RequestPredicates.path("/api/order"),
	 * HandlerFunctions.http("http://localhost:8081"))
	 * .filter(CircuitBreakerFilterFunctions.circuitBreaker(
	 * "orderServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
	 * .build();
	 * 
	 * }
	 * 
	 * @Bean public RouterFunction<ServerResponse> inventoryServiceRoute() {
	 * 
	 * return GatewayRouterFunctions .route("inventory_service")
	 * .route(RequestPredicates.path("/api/inventory"),
	 * HandlerFunctions.http("http://localhost:8082"))
	 * .filter(CircuitBreakerFilterFunctions.circuitBreaker(
	 * "inventoryServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
	 * .build();
	 * 
	 * }
	 * 
	 * @Bean public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
	 * return GatewayRouterFunctions.route("product_service_swagger")
	 * .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"),
	 * HandlerFunctions.http("http://localhost:8095"))
	 * .filter(CircuitBreakerFilterFunctions.circuitBreaker(
	 * "productServiceSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
	 * .filter(setPath("/api-docs")) .build(); }
	 * 
	 * @Bean public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
	 * return GatewayRouterFunctions.route("order_service_swagger")
	 * .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"),
	 * HandlerFunctions.http("http://localhost:8081"))
	 * .filter(CircuitBreakerFilterFunctions.circuitBreaker(
	 * "orderServiceSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
	 * .filter(setPath("/api-docs")) .build(); }
	 * 
	 * @Bean public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
	 * return GatewayRouterFunctions.route("inventory_service_swagger")
	 * .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"),
	 * HandlerFunctions.http("http://localhost:8082"))
	 * .filter(CircuitBreakerFilterFunctions.circuitBreaker(
	 * "inventoryServiceSwaggerCircuitBreaker",
	 * URI.create("forward:/fallbackRoute"))) .filter(setPath("/api-docs"))
	 * .build(); }
	 */
	
	@Bean
	public RouterFunction<ServerResponse> fallbackRoute() {
	    return route("fallbackRoute")
	        .GET("/fallbackRoute", request ->
	            ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
	                .body("Service Unavailable, please try again later"))
	        .POST("/fallbackRoute", request ->
	            ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
	                .body("Service Unavailable, please try again later"))
	        .build();
	}



}
