package com.ars.gateway.business.concretes;


import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ars.gateway.business.abstracts.UserService;
import com.ars.gateway.business.dto.CredentialsDto;
import com.ars.gateway.business.dto.UserDto;
import com.ars.gateway.business.request.RegisterRequest;
import com.ars.gateway.business.request.UserAdd;
import com.ars.gateway.config.UserAuthProvider;
import com.ars.gateway.config.UserAuthenticationProvider;
import com.ars.gateway.entities.User;
import com.ars.gateway.entities.UserInfo;
import com.ars.gateway.exception.AppException;
import com.ars.gateway.mappers.UserMapper;
import com.ars.gateway.repository.UserRepositories;
import com.ars.gateway.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService,UserDetailsService{
   
private final UserRepository userRepository;
	private final UserRepositories userRepositories;
	private final PasswordEncoder passwordEncoder;
	private final  UserMapper userMapper;
	private final UserAuthProvider userAuthProvider;
	private final UserAuthenticationProvider userAuthenticationProvider;
   

    // Mutlak yol kullanarak dizin oluşturun
    private final String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "profile-pictures").toString();
    


    public UserInfo registerUser(RegisterRequest registerRequest) throws IOException {
        // Profil fotoğrafını kaydetme
        String profilePictureUrl = null;
        if (registerRequest.file() != null) {
            // Dosyayı sunucuda kaydetme
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = registerRequest.email() + "_" + registerRequest.username()
            					+ registerRequest.file().getOriginalFilename()
            					.substring(registerRequest.file()
            					.getOriginalFilename().lastIndexOf("."));
            
            Path filePath = uploadPath.resolve(fileName);
            registerRequest.file().transferTo(filePath.toFile());

            // Fotoğraf URL'sini oluşturma
            profilePictureUrl = "/uploads/profile-pictures/" + fileName;
        }

        // Kullanıcıyı kaydetme
        UserInfo user = new UserInfo();
        
        user.setName(registerRequest.name());
        user.setSurname(registerRequest.surname());
        user.setStatus(registerRequest.status());
        
        user.setIp(registerRequest.ip());
        user.setPort(registerRequest.port());
        user.setEmail(registerRequest.email());
        user.setPassword(registerRequest.password());
        
        user.setUsername(registerRequest.username());
        user.setPhone(registerRequest.phone());
        
        user.setProfilePictureUrl(profilePictureUrl);

        return userRepository.save(user);
    }

    


	//Şifrenin doğruluğunun kontrolü burada sağlanıyor
	public UserDto login(CredentialsDto credentialDto) {
		
		User user =userRepositories.findByLogin(credentialDto.login())
						.orElseThrow(() -> new AppException("Kullanıcı Yok",HttpStatus.NOT_FOUND));
		
		
		if (passwordEncoder.matches(CharBuffer.wrap(credentialDto.password()), user.getPassword()))
		{
			UserDto dto = UserDto.builder()
				.id(0)
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.login(user.getLogin()).build();				
					
			
			dto.setToken(userAuthenticationProvider.createToken(dto));
			
			return dto;
		}
		throw new AppException("Geçersiz Şifre",HttpStatus.BAD_REQUEST);
		
	}
	
	
	//Şifre Güncelleme methodu
	@Override
	public String encode(String password) 
	{
		String a= passwordEncoder.encode(password);
		//System.out.println(a);
		return a;
	}
	
	

	//Token Kontrol methodu
	public UserDto userStatus(String token) {
		
		UserDto dto= userAuthProvider.decodeToken(token);
		
		return dto;
	}


	@Override
	public List<User> getAll() {
		List<User> users = userRepositories.findAll();
		return users;
	}


	@Override
	public UserDto add(UserAdd userAdd) {
		
		
		CredentialsDto credentialDto = CredentialsDto.builder()
				.login(userAdd.login())
				.password(userAdd.password().toCharArray())
				.build();
		
		User user = new User();
		
		user.setFirstName(userAdd.firstName());
		user.setLastName(userAdd.lastName());
		user.setLogin(userAdd.login());
		user.setPassword(passwordEncoder.encode(userAdd.password()));
		
		userRepositories.save(user);
		
		UserDto userDro = this.login(credentialDto);
		
		return userDro;
	}


	@Override
	public void delete(long id) {
		
		userRepositories.deleteById(id);
		
	}

	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public String getUsernameFromToken(String token) {

		String username = userAuthenticationProvider.getUsernameFromToken(token);
		
		
		return username;
	}
    
    
}
