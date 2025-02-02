package com.ars.user.webApi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ars.user.business.abstracts.SettingService;
import com.ars.user.business.request.AccountSettingRequest;
import com.ars.user.business.request.NotificationRequest;
import com.ars.user.business.request.SupportRequest;
import com.ars.user.business.response.AccountSettinResponse;
import com.ars.user.business.response.ChatResponse;
import com.ars.user.business.response.NotificationResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class SettingController {

	private final SettingService service;

	@GetMapping("/account-setting")
	public ResponseEntity<?> getAccountSetting(HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		AccountSettinResponse response = service.getAccountSetting(extractedToken);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/set-account-setting")
	public ResponseEntity<?> setAccountSetting(@RequestBody AccountSettingRequest accountSettingRequest,
			HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		service.setAccountSetting(accountSettingRequest, extractedToken);

		return ResponseEntity.ok("Successful");
	}

	@GetMapping("/chat-setting")
	public ResponseEntity<?> getChatSetting(HttpServletRequest request) {
		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		List<ChatResponse> response = service.getChatSetting(extractedToken);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/notification")
	public ResponseEntity<?> getNotification(HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		NotificationResponse response = service.getNotification(extractedToken);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/set-notification")
	public ResponseEntity<?> setNotification(@RequestBody NotificationRequest notificationRequest,
			HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		service.settNotification(notificationRequest, extractedToken);

		return ResponseEntity.ok("Successful");
	}

	@PostMapping("/add-support")
	public ResponseEntity<?> addSupport(@RequestBody SupportRequest SupportRequest, HttpServletRequest request) {

		// Header'dan token al
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body("Token bulunamadı.");
		}

		// Token'ı temizle (eğer "Bearer " ile başlıyorsa)
		String extractedToken = token.replace("Bearer ", "");

		service.support(SupportRequest, extractedToken);

		return ResponseEntity.ok(SupportRequest.title() + " Successful");
	}

}
