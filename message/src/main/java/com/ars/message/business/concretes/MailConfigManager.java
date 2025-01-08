package com.ars.message.business.concretes;

import org.springframework.stereotype.Service;

import com.ars.message.business.abstracts.MailConfigService;
import com.ars.message.entities.MailServerConfig;
import com.ars.message.repository.MailConfigRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailConfigManager implements MailConfigService {

	private final MailConfigRepository mailConfigRepository;
	
	@Override
	public MailServerConfig getMailServerConfigForUser(String username) {

		
		
		
		     return mailConfigRepository.findByMailUser(username);
	}

}
