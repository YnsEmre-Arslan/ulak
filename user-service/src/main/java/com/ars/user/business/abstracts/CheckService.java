package com.ars.user.business.abstracts;

import com.ars.user.business.request.CheckRequest;

public interface CheckService {
	
	String checkMail(CheckRequest checkRequest);

}
