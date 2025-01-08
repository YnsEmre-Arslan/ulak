package com.ars.gateway.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ars.gateway.entities.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {

}
