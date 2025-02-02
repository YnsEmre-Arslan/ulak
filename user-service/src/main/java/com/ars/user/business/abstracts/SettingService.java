package com.ars.user.business.abstracts;

import java.util.List;

import com.ars.user.business.request.AccountSettingRequest;
import com.ars.user.business.request.NotificationRequest;
import com.ars.user.business.request.SupportRequest;
import com.ars.user.business.response.AccountSettinResponse;
import com.ars.user.business.response.ChatResponse;
import com.ars.user.business.response.NotificationResponse;

public interface SettingService {
	
	
	AccountSettinResponse getAccountSetting(String token);
	
	List<ChatResponse> getChatSetting(String token);
	
	NotificationResponse getNotification(String token);
	
	void setAccountSetting(AccountSettingRequest accountSettingRequest,String token);
	
	void settNotification(NotificationRequest notificationRequest,String token);
	
	public String support( SupportRequest SupportRequest, String token);
	

}
