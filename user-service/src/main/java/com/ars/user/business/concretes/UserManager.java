package com.ars.user.business.concretes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.ars.user.business.abstracts.UserService;
import com.ars.user.business.dto.UserDto;
import com.ars.user.business.request.UserAdd;
import com.ars.user.business.request.UserRequest;
import com.ars.user.business.response.UserInfoResponse;
import com.ars.user.client.RegisteryService;
import com.ars.user.entities.Contac;
import com.ars.user.entities.Notification;
import com.ars.user.entities.User;
import com.ars.user.repository.ContacRepository;
import com.ars.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

	private final UserRepository userRepository;
	private final ContacRepository contacRepository;
	private final RegisteryService registeryService;

	private final String uploadDir = System.getProperty("user.dir") + "/uploads/profile-pictures/";

	public UserDto registerUser(UserRequest userRequest) throws IOException {

		// Profil fotoğrafını kaydetme
		String profilePictureUrl = null;

		if (userRequest.file() != null) {

			// Dosyayı sunucuda kaydetme
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalFilename = userRequest.file().getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // Dosya uzantısını al

			String fileName = userRequest.username() + "." + extension; // E-posta ile dosya uzantısını birleştir

			Path filePath = uploadPath.resolve(fileName);

			userRequest.file().transferTo(filePath.toFile());

			// Fotoğraf URL'sini oluşturma
			profilePictureUrl = "https://ulak.arst.tr:8443/" + "uploads/profile-pictures/" + fileName;
		}

		// E-posta adresi kontrolü
		if (userRepository.findByEmail(userRequest.email()).isPresent()) {
			throw new IllegalArgumentException("Bu e-posta adresi zaten kullanılıyor: " + userRequest.email());
		}

		// Kullanıcı adı kontrolü
		if (userRepository.findByUsername(userRequest.username()) != null) {
			throw new IllegalArgumentException("Bu kullanıcı adı zaten kullanılıyor: " + userRequest.username());
		}

		// Kullanıcıyı kaydetme
		User user = new User();
		user.setName(userRequest.name());
		user.setSurname(userRequest.surname());
		user.setStatus(userRequest.status());

		user.setHost(userRequest.ip());
		user.setPort(userRequest.port());
		user.setEmail(userRequest.email());
		user.setPassword(userRequest.password());

		user.setUsername(userRequest.username());
		user.setPhone(userRequest.phone());

		user.setProfilePictureUrl(profilePictureUrl);

		Notification notification = Notification.builder().app(true).message(true).news(true).mail(true).build();

		user.setNotification(notification);

		UserAdd RegisteryUser = UserAdd.builder().firstName(userRequest.name()).lastName(userRequest.surname())
				.login(userRequest.username()).password(userRequest.password()).build();

		UserDto response = registeryService.addUser(RegisteryUser);

		userRepository.save(user);

		Contac contac = Contac.builder().userContact(user.getUsername()).name("Ulak").surname("Haberleşme")
				.mailAddress("info@arst.tr").imageUrl("http://ulak.arst.tr:8443/uploads/profile-pictures/ulak.png")
				.build();

		contacRepository.save(contac);

		return response;
	}

	@Override
	public UserInfoResponse getUserConfig(String username) {

		User user = userRepository.findByUsername(username);

		UserInfoResponse userIndoResponse = UserInfoResponse.builder()

				.id(user.getId().longValue()).host(user.getHost()).port(user.getPort()).username(user.getUsername())
				.password(user.getPassword()).protocol("").mailUser(user.getUsername()).build();

		return userIndoResponse;
	}

}
