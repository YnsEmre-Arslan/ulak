package com.ars.message.business.abstracts;

import com.ars.message.entities.MailServerConfig;

public interface MailConfigService {

    public MailServerConfig getMailServerConfigForUser(String username);
}
