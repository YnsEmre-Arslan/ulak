package com.ars.user.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ars.user.business.request.DeleteMessageRequest;
import com.ars.user.business.request.SendEmailRequest;
import com.ars.user.business.request.UpdateMessageRequest;
import com.ars.user.business.response.ContactResponse;
import com.ars.user.business.response.UserInfoResponse;

@Service
public class MessageService {

	private final WebClient webClient;

	public MessageService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://ulak.arst.tr:8087").build(); // API'nizin base URL'i
	}

	public List<ContactResponse> getMail(UserInfoResponse userInfoResponse) {
		try {
			return webClient.post().uri("/mail/analyze")
					//.header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Token'ı
					// başlığa ekliyoruz
					.bodyValue(userInfoResponse) // Body kısmı
					.retrieve().bodyToMono(new ParameterizedTypeReference<List<ContactResponse>>() {
					}).block(); // Bloklamak, eşzamanlı işlem için
		} catch (WebClientResponseException ex) {
			// Hata durumlarını ele alıyoruz
			throw new RuntimeException("Error retrieving username: " + ex.getMessage(), ex);
		}
	}
	
	
    public String sendMail(SendEmailRequest sendMailRequest) {
        try {
            return webClient.post()
                    .uri("/mail/send")
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(sendMailRequest) // Body kısmı
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error sent mail to : " + ex.getMessage(), ex);
        }
    }

    
    public String updateMail(UpdateMessageRequest updateMessageRequest ) {
        try {
            return webClient.post()
                    .uri("/mail/update")
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(updateMessageRequest) // Body kısmı
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error update mail to : " + ex.getMessage(), ex);
        }
    }
    
    public String deleteMail( DeleteMessageRequest deleteMessageRequest) {
        try {
            return webClient.post()
                    .uri("/mail/delete")
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token()) // Token'ı başlığa ekliyoruz
                    .bodyValue(deleteMessageRequest) // Body kısmı
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloklamak, eşzamanlı işlem için
        } catch (WebClientResponseException ex) {
            // Hata durumlarını ele alıyoruz
            throw new RuntimeException("Error delete mail to : " + ex.getMessage(), ex);
        }
    }















}
