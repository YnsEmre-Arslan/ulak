package com.ars.user.business.request;

public record NotificationRequest(
		
	     Boolean message,
		
	     Boolean mail,
		
	     Boolean news,
		
	     Boolean app
		
		) {

}