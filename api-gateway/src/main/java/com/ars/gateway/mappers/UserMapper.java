package com.ars.gateway.mappers;



import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import com.ars.gateway.business.dto.UserDto;
import com.ars.gateway.entities.User;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface UserMapper {
	 
		UserDto toUserDto(User user);
	
}
