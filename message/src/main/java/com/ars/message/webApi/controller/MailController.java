package com.ars.message.webApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ars.message.business.concretes.MailReaderManager;
import com.ars.message.business.response.ContactResponse;
import com.ars.message.business.response.UserInfoResponse;

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailReaderManager mailReader;

	@PostMapping("/analyze")
	public ResponseEntity<?> analyzeMails(@RequestBody UserInfoResponse userInfoResponse) {
		try {

			// Mail okuma işlemini başlat
			List<ContactResponse> userMessages = mailReader.readAndGroupMails(userInfoResponse);

			return ResponseEntity.ok(userMessages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error analyzing mails: " + e.getMessage());
		}
	}

}
