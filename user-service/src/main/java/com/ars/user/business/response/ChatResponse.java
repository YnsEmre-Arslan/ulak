package com.ars.user.business.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {

	private String name;
	
	private String surname;

	private String email;

	private String photoUrl;

	private Boolean isEditing;

}
