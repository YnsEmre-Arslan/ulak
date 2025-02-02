package com.ars.gateway.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final UserAuthProvider userAuthProvider;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)throws IOException, ServletException {
		
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header != null) {

			String[] authElements = header.split(" ");

			if (authElements.length == 2 && "Bearer".equals(authElements[0])) {

				try {
					SecurityContextHolder.getContext()
								.setAuthentication(userAuthProvider.validateToken(authElements[1]));
				} catch (RuntimeException e) {
					SecurityContextHolder.clearContext();
					throw e;
				}

			}
		}

		filterChain.doFilter(request, response);

	}
}
