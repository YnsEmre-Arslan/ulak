package com.ars.user.webApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ars.user.business.abstracts.CheckService;
import com.ars.user.business.request.CheckRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class CheckController {

	private final CheckService checkService;

	@PostMapping("/check")
	public ResponseEntity<?> registerUser(@RequestBody CheckRequest CheckRequest) {

		try {
			String response = checkService.checkMail(CheckRequest);

			return ResponseEntity.ok(response);
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}

}
