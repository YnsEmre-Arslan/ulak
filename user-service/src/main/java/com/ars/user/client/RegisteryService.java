package com.ars.user.client;

import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ars.user.business.dto.UserDto;
import com.ars.user.business.request.TokenRequest;
import com.ars.user.business.request.UserAdd;

@Service
public class RegisteryService {

    private final WebClient webClient;
    
    


    public RegisteryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://ulak.arst.tr:8443").build(); // API'nizin base URL'i
    }

    public UserDto addUser(UserAdd userAdd) {
        ResponseEntity<UserDto> response = webClient.post()
        		
                .uri("/add")  // API'nizin path'ini buraya yazın
                
                .bodyValue(userAdd)  // UserAdd nesnesini JSON olarak gönderiyoruz
                
                .retrieve()
                .toEntity(UserDto.class)  // Yanıtı UserDto olarak dönüştürüyoruz
                
                .block();  // Bekleme (synchronous) işlemi yapıyoruz, Reactive kullanıyorsanız block() yerine subscribe() kullanabilirsiniz.

        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
        	throw new RuntimeException("İstek başarısız: " +
                    (response != null ? response.getStatusCode() : "Unknown error"));
        	}
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


