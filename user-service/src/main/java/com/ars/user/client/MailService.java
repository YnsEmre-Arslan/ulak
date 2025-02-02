package com.ars.user.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ars.user.business.request.LoginCheckRequest;
import com.ars.user.business.request.SupportClientRequest;

@Service
public class MailService {
	
    private final WebClient webClient;
    
    public MailService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://ulak.arst.tr:8085").build(); // API'nizin base URL'i
    }

    
    
    public String getMail(LoginCheckRequest logRequest) {
        try {
            return webClient.post()
                    .uri("/check/send")
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(logRequest) // Body kısmı
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error retrieving username: " + ex.getMessage(), ex);
        }
    }
    
    
    
    
    public String support(SupportClientRequest supportClientRequest) {
        try {
            return webClient.post()
                    .uri("/check/send/support")
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(supportClientRequest) // Body kısmı
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error retrieving username: " + ex.getMessage(), ex);
        }
    }
    
    
    
}
