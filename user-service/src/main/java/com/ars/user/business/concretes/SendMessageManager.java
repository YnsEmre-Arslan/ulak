package com.ars.user.business.concretes;

import org.springframework.stereotype.Service;

import com.ars.user.business.abstracts.SendMessageService;
import com.ars.user.business.request.DeleteMessageRequest;
import com.ars.user.business.request.MessageDelete;
import com.ars.user.business.request.MessageUpdate;
import com.ars.user.business.request.SendEmailRequest;
import com.ars.user.business.request.SendMessageRequest;
import com.ars.user.business.request.TokenRequest;
import com.ars.user.business.request.UpdateMessageRequest;
import com.ars.user.client.MessageService;
import com.ars.user.client.RegisteryService;
import com.ars.user.entities.User;
import com.ars.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendMessageManager implements SendMessageService {

	private final RegisteryService registeryService;
	private final UserRepository userRepository;
	private final MessageService messageService;

	@Override
	public String sendMessage(SendMessageRequest sendMessgeRequest, TokenRequest token) {

		String username = registeryService.getUsername(token);

		User user = userRepository.findByUsername(username);

		SendEmailRequest sendEmailRequest = SendEmailRequest.builder()

				.host(user.getHost()).port(587).from(user.getEmail())
				.password(user.getPassword()).to(sendMessgeRequest.to()).body(sendMessgeRequest.message()).build();

		messageService.sendMail(sendEmailRequest);

		return username;
	}
	
	
	@Override
	public String updateMail(MessageUpdate messageUpdate, TokenRequest token) {

		String username = registeryService.getUsername(token);

		User user = userRepository.findByUsername(username);
		
		UpdateMessageRequest updateMessageRequest = UpdateMessageRequest.builder()
				.messageId(messageUpdate.messageId())
				.host(user.getHost())
				.username(user.getEmail())
				.password(user.getPassword())
				.newText(messageUpdate.newText()).build();

		String newId =  messageService.updateMail(updateMessageRequest);

		return newId;
	}

	
	
	
	@Override
	public String deleteMail(MessageDelete messageDelete, TokenRequest token) {

		String username = registeryService.getUsername(token);

		User user = userRepository.findByUsername(username);
		
		
		DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
				.messageId(messageDelete.id())
				.host(user.getHost())
				.username(user.getEmail())
				.password(user.getPassword()).build();

		String response = messageService.deleteMail(deleteMessageRequest);

		return response;
	}
	
	

}
