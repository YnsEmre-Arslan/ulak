package com.ars.gateway.business.abstracts;


import java.io.IOException;
import java.util.List;

import com.ars.gateway.business.dto.CredentialsDto;
import com.ars.gateway.business.dto.UserDto;
import com.ars.gateway.business.request.RegisterRequest;
import com.ars.gateway.business.request.UserAdd;
import com.ars.gateway.entities.User;
import com.ars.gateway.entities.UserInfo;

public interface UserService {

	
    public UserInfo registerUser(RegisterRequest registerRequest) throws IOException;
    
    String getUsernameFromToken(String token);
    
	UserDto login(CredentialsDto credentialDto);
	
	UserDto userStatus(String token);

	String encode(String password);
	
	List<User> getAll();
	
	UserDto add(UserAdd userAdd);
	
	void delete(long id);
    
    
    
}
