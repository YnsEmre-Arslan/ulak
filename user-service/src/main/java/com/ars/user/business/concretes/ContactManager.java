package com.ars.user.business.concretes;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ars.user.business.abstracts.ContactService;
import com.ars.user.business.request.ContactAddRequest;
import com.ars.user.business.request.TokenRequest;
import com.ars.user.business.response.ContactResponse;
import com.ars.user.business.response.UserInfoResponse;
import com.ars.user.client.MessageService;
import com.ars.user.client.RegisteryService;
import com.ars.user.entities.Contac;
import com.ars.user.entities.User;
import com.ars.user.repository.ContacRepository;
import com.ars.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactManager implements ContactService {

	private final RegisteryService registeryService;
	private final ContacRepository contacRepository;
	private final UserRepository userRepository;
	private final MessageService messageService;

	@Override
	public String addContact(ContactAddRequest addRequest, TokenRequest tokenRequest) {

		String username = registeryService.getUsername(tokenRequest);

		Boolean status = contacRepository.existsByUserContactAndMailAddress(username, addRequest.mailAddress());

		if (!status) {
			Contac contac = Contac.builder().userContact(username).name(addRequest.name()).surname(addRequest.surname())
					.mailAddress(addRequest.mailAddress())
					.imageUrl("http://ulak.arst.tr:8443/uploads/profile-pictures/empty.png").build();

			contacRepository.save(contac);
		} else
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration already exists");

		return "Ok";
	}

	@Override
	public List<ContactResponse> getContact(TokenRequest tokenRequest) {

		String username = registeryService.getUsername(tokenRequest);

		User user = userRepository.findByUsername(username);

		UserInfoResponse userInfoResponse = UserInfoResponse.builder()

				.id(user.getId().longValue()).host(user.getHost()).port(user.getPort()).username(user.getUsername())
				.password(user.getPassword()).protocol("imaps").mailUser(user.getEmail())

				.build();

		List<ContactResponse> userMessages = messageService.getMail(userInfoResponse);

		for (ContactResponse userMessage : userMessages) {

			Contac contact = contacRepository.findByUserContactAndMailAddress(username, userMessage.getMailAddress());

			if (contact != null) {
				
				userMessage.setImageUrl(

						userRepository.findProfilePictureUrlByEmail(contact.getMailAddress())
								.orElse("https://ulak.arst.tr:8443/uploads/profile-pictures/empty.png")

				);
				userMessage.setName(contact.getName() + " " + contact.getSurname());
				userMessage.setSurname(contact.getSurname());

			} else {
				
				
				userMessage.setImageUrl(

						userRepository.findProfilePictureUrlByEmail(userMessage.getMailAddress())
						.orElse("https://ulak.arst.tr:8443/uploads/profile-pictures/empty.png")

				);
				userMessage.setName(userMessage.getMailAddress());

			}

		}

		return userMessages;

	}

	@Override
	public String deleteContact(ContactAddRequest addRequest, TokenRequest tokenRequest) {

		String username = registeryService.getUsername(tokenRequest);

		contacRepository.deleteByUserContactAndMailAddress(username, addRequest.mailAddress());

		return "Ok";
	}

	@Override
	public String updateContact(ContactAddRequest addRequest, TokenRequest tokenRequest) {

		String username = registeryService.getUsername(tokenRequest);

		Contac contac = contacRepository.findByUserContactAndMailAddress(username, addRequest.mailAddress());

		contac.setMailAddress(addRequest.mailAddress());
		contac.setName(addRequest.name());

		contacRepository.save(contac);

		return "Ok";
	}

}
