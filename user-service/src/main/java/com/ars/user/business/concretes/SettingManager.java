package com.ars.user.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ars.user.business.abstracts.SettingService;
import com.ars.user.business.request.AccountSettingRequest;
import com.ars.user.business.request.NotificationRequest;
import com.ars.user.business.request.SupportClientRequest;
import com.ars.user.business.request.SupportRequest;
import com.ars.user.business.request.TokenRequest;
import com.ars.user.business.response.AccountSettinResponse;
import com.ars.user.business.response.ChatResponse;
import com.ars.user.business.response.NotificationResponse;
import com.ars.user.client.MailService;
import com.ars.user.client.RegisteryService;
import com.ars.user.entities.Contac;
import com.ars.user.entities.User;
import com.ars.user.repository.ContacRepository;
import com.ars.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SettingManager implements SettingService {

	private final RegisteryService registeryService;

	private final UserRepository userRepository;

	private final ContacRepository contacRepository;

	private final MailService mailService;

	@Override
	public AccountSettinResponse getAccountSetting(String token) {

		TokenRequest tokenRequest = TokenRequest.builder().token(token).build();

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		AccountSettinResponse response = AccountSettinResponse.builder()

				.imageUrl(user.getProfilePictureUrl()).username(user.getUsername()).statusText(user.getStatus())
				.name(user.getName()).surname(user.getSurname()).host(user.getHost()).mailAddress(user.getEmail())
				.mailPassword(user.getPassword()).build();

		return response;
	}

	@Override
	public void setAccountSetting(AccountSettingRequest accountSettingRequest, String token) {

		TokenRequest tokenRequest = TokenRequest.builder().token(token).build();

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		user.setStatus(accountSettingRequest.statusText());
		user.setName(accountSettingRequest.name());
		user.setHost(accountSettingRequest.host());
		user.setPassword(accountSettingRequest.mailPassword());

		userRepository.save(user);

	}

	@Override
	public List<ChatResponse> getChatSetting(String token) {
		
		System.out.println("Token   " + token);

		List<ChatResponse> chatResponses = new ArrayList<ChatResponse>();

		TokenRequest tokenRequest = TokenRequest.builder().token(token).build();

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		List<Contac> contacs = contacRepository.findByUserContact(user.getUsername());

		for (Contac contac : contacs) {
			ChatResponse chatResponse = ChatResponse.builder().email(contac.getMailAddress())
					.name(contac.getName() + " " + contac.getSurname()).surname(contac.getSurname())
					.photoUrl(contac.getImageUrl()).isEditing(false).build();

			chatResponses.add(chatResponse);
		}

		return chatResponses;
	}

	@Override
	public NotificationResponse getNotification(String token) {

		TokenRequest tokenRequest = TokenRequest.builder().token(token).build();

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		NotificationResponse response = NotificationResponse.builder()

				.message(user.getNotification().getMessage()).mail(user.getNotification().getMail())
				.news(user.getNotification().getNews()).app(user.getNotification().getApp()).build();

		return response;
	}

	@Override
	public void settNotification(NotificationRequest notificationRequest, String token) {

		TokenRequest tokenRequest = TokenRequest.builder().token(token).build();

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		user.getNotification().setApp(notificationRequest.app());
		user.getNotification().setMail(notificationRequest.mail());
		user.getNotification().setMessage(notificationRequest.message());
		user.getNotification().setNews(notificationRequest.news());

		userRepository.save(user);

	}

	@Override
	public String support(SupportRequest supportRequest, String token) {

		TokenRequest tokenRequest = TokenRequest.builder().token(token).build();

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		userRepository.save(user);

		SupportClientRequest supportClientRequest = SupportClientRequest.builder()

				.name(user.getName() + " " + user.getSurname()).email(user.getEmail()).title(supportRequest.title())
				.subject(supportRequest.subject()).build();

		String response = mailService.support(supportClientRequest);

		return response;

	}

}
