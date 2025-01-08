package com.ars.user.business.request;

public record ContactAddRequest(
		String name,  
		String surname,
		String mailAddress) {}
