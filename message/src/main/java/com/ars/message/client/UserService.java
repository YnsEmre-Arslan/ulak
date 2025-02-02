package com.ars.message.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ars.message.business.response.UserInfoResponse;

@Service
public class UserService {

	private final WebClient webClient;

	public UserService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://ulak.arst.tr:8086").build(); // API'nizin base URL'i
	}


	
    public UserInfoResponse getConfig(String username) {
        try {
            return webClient.post()
                    .uri("/users/config")
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(username) // Body kısmı
                    .retrieve()
                    .bodyToMono(UserInfoResponse.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error sent mail to : " + ex.getMessage(), ex);
        }
     
    } 


}
