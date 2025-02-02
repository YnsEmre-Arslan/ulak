package com.ars.user.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class CustomFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomFilter.class); // Logger oluşturuluyor

    private static final String ALLOWED_IP = "127.0.0.1";  // localhost
    private static final String GATEWAY_IP = "https://ulak.arst.tr/"; // Gateway IP adresi
    private static final int BLOCKED_PORT = 8086; // Engellenmesi gereken port

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String remoteHost = request.getRemoteAddr();
        int remotePort = request.getRemotePort();

        // Eğer gelen IP gateway IP'si veya localhost ise ve port 8086 değilse, devam et
        if ((GATEWAY_IP.equals(remoteHost) || ALLOWED_IP.equals(remoteHost)) && remotePort != BLOCKED_PORT) {
            filterChain.doFilter(request, response); // Devam et
        } else {
            // Erişim engellendiğinde IP adresini ve portu logla
            logger.warn("Erişim engellendi! IP: {}, Port: {}", remoteHost, remotePort);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Erişim engellendi");
        }
    }
}

 