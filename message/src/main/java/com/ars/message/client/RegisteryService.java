package com.ars.message.client;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ars.message.business.request.TokenRequest;


@Service
public class RegisteryService {

    private final WebClient webClient;
    
    

    public RegisteryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://ulak.arst.tr:8443").build(); // API'nizin base URL'i
    }


    
    
    public String getUsername(TokenRequest token) {
        try {
            return webClient.post()
                    .uri("/username")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(token) // Body kısmı
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error retrieving username: " + ex.getMessage(), ex);
        }
    }
    }


