package com.ars.user.business.abstracts;

import java.io.IOException;

import com.ars.user.business.dto.UserDto;
import com.ars.user.business.request.UserRequest;
import com.ars.user.business.response.UserInfoResponse;

public interface UserService  {

	
 public UserDto registerUser(UserRequest userRequest) throws IOException;
 
 public UserInfoResponse getUserConfig(String username);
 
 
 
}
