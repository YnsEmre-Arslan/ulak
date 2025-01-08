package com.ars.user.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.ars.user.business.response.ContactResponse;
import com.ars.user.entities.Contac;

@Mapper(componentModel = "spring")
public interface ContactMapper {
	
	
    List<ContactResponse> toContactResponseList(List<Contac> contacs);


}
