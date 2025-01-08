package com.ars.user.webApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ars.user.business.abstracts.SendMessageService;
import com.ars.user.business.request.MessageDelete;
import com.ars.user.business.request.MessageUpdate;
import com.ars.user.business.request.SendMessageRequest;
import com.ars.user.business.request.TokenRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class SendMessageController {

	private final SendMessageService sendMessageService;

	@PostMapping("/send-message")
	public ResponseEntity<?> sendMail(@RequestBody SendMessageRequest sendMessageRequest,
			HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

		sendMessageService.sendMessage(sendMessageRequest, tokenRequest);

		return ResponseEntity.ok("Successful");
	}
	
	
	@PostMapping("/delete-message")
	public ResponseEntity<?> deleteMail(@RequestBody MessageDelete messageDelete,
			HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

		sendMessageService.deleteMail(messageDelete, tokenRequest);

		return ResponseEntity.ok("Successful");
	}
	
	
	
	
	@PostMapping("/update-message")
	public ResponseEntity<?> updateMail(@RequestBody MessageUpdate messageUpdate,
			HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

		String newId = sendMessageService.updateMail(messageUpdate, tokenRequest);

		return ResponseEntity.ok(newId);
	}

}
