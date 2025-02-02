package com.ars.user.business.abstracts;

import java.util.List;

import com.ars.user.business.request.ContactAddRequest;
import com.ars.user.business.request.TokenRequest;
import com.ars.user.business.response.ContactResponse;

public interface ContactService {

	
	String addContact(ContactAddRequest addRequest,TokenRequest tokenRequest);
	
	String deleteContact(ContactAddRequest addRequest,TokenRequest tokenRequest);
	
	String updateContact(ContactAddRequest addRequest,TokenRequest tokenRequest);


	List<ContactResponse> getContact(TokenRequest tokenRequest);

}
