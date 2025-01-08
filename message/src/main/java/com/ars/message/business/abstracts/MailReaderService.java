package com.ars.message.business.abstracts;

import com.ars.message.entities.MailServerConfig;

public interface MailReaderService {

	
    public void readMails(MailServerConfig config) throws Exception;
}
