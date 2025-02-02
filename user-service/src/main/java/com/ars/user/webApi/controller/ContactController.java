package com.ars.user.webApi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ars.user.business.abstracts.ContactService;
import com.ars.user.business.request.ContactAddRequest;
import com.ars.user.business.request.TokenRequest;
import com.ars.user.business.response.ContactResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ContactController {

	private final ContactService contactService;

	@PostMapping("/add")
	public ResponseEntity<?> registerUser(@RequestBody ContactAddRequest contactAddRequest,
			HttpServletRequest request) {
		try {
			// Header'dan token al
			String token = request.getHeader("Authorization");

			if (token == null || token.isEmpty()) {
				return ResponseEntity.status(400).body("Token bulunamadı.");
			}

			// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
			String extractedToken = token.replace("Bearer ", "");

			TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

			// İşlem yap ve veriyi kaydet
			contactService.addContact(contactAddRequest, tokenRequest);

			return ResponseEntity.ok("Successful registration");
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestBody ContactAddRequest contactAddRequest, HttpServletRequest request) {
		try {
			// Header'dan token al
			String token = request.getHeader("Authorization");

			if (token == null || token.isEmpty()) {
				return ResponseEntity.status(400).body("Token bulunamadı.");
			}

			// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
			String extractedToken = token.replace("Bearer ", "");

			TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

			// İşlem yap ve veriyi kaydet
			contactService.deleteContact(contactAddRequest, tokenRequest);

			return ResponseEntity.ok("Successful registration");
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody ContactAddRequest contactAddRequest, HttpServletRequest request) {
		try {
			// Header'dan token al
			String token = request.getHeader("Authorization");

			if (token == null || token.isEmpty()) {
				return ResponseEntity.status(400).body("Token bulunamadı.");
			}

			// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
			String extractedToken = token.replace("Bearer ", "");

			TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

			// İşlem yap ve veriyi kaydet
			contactService.updateContact(contactAddRequest, tokenRequest);

			return ResponseEntity.ok("Successful registration");
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}

	@GetMapping("/contacts")
	public ResponseEntity<?> getContacts(HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		TokenRequest tokenRequest = TokenRequest.builder().token(extractedToken).build();

		List<ContactResponse> contactResponses = contactService.getContact(tokenRequest);

		return ResponseEntity.ok(contactResponses);
	}

}
