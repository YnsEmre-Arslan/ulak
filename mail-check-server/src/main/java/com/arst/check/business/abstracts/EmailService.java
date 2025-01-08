package com.arst.check.business.abstracts;

import com.arst.check.business.request.LoginCheckRequest;
import com.arst.check.business.request.SupportRequest;

public interface EmailService {

	
	public void checkEmail(LoginCheckRequest LoginCheckRequest);
	
    public void sendSupportEmail(SupportRequest supportRequest);
    
    public void sendEmailToSupportTeam(SupportRequest supportRequest, String additionalInfo);
}
