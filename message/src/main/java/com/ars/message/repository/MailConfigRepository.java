package com.ars.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ars.message.entities.MailServerConfig;


public interface MailConfigRepository extends JpaRepository<MailServerConfig, Long> {

    MailServerConfig findByMailUser(String mailUser);

	
}
