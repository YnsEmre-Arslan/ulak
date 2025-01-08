package com.ars.user.business.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactResponse {
		   
	
    private List<MessageResponse> messages;

    private String imageUrl;
	
    private String name;
	
    private String surname;

    private String mailAddress;

}