package com.ars.user.business.abstracts;

import com.ars.user.business.request.MessageDelete;
import com.ars.user.business.request.MessageUpdate;
import com.ars.user.business.request.SendMessageRequest;
import com.ars.user.business.request.TokenRequest;

public interface SendMessageService {
	
	String sendMessage(SendMessageRequest sendMessgeRequest, TokenRequest token);
	public String updateMail(MessageUpdate messageUpdate, TokenRequest token);
	public String deleteMail(MessageDelete messageDelete, TokenRequest token);

}
