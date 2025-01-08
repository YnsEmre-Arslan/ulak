package com.arst.check.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.arst.check.entities.MailConfig;


public interface MailConfigRepository extends JpaRepository<MailConfig, Long> {

    @Query("SELECT m FROM MailConfig m WHERE m.id = :id OR :id = 0")
    MailConfig findByIdOrDefault(int id);
	
}
