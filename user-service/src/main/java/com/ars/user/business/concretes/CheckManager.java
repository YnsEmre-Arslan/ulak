package com.ars.user.business.concretes;

import org.springframework.stereotype.Service;

import com.ars.user.business.abstracts.CheckService;
import com.ars.user.business.request.CheckRequest;
import com.ars.user.business.request.LoginCheckRequest;
import com.ars.user.client.MailService;
import com.ars.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckManager implements CheckService {

	private final MailService mailService;
	private final UserRepository userRepository;

	@Override
	public String checkMail(CheckRequest checkRequest) {

		String mail = userRepository.findEmailByUsername(checkRequest.username());

		LoginCheckRequest request = LoginCheckRequest.builder().name(checkRequest.username()).code(checkRequest.code())
				.to(mail).template("template-1").build();

		String response = mailService.getMail(request);

		return response;
	}

}
