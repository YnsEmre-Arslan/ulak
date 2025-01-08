package com.ars.gateway.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ars.gateway.entities.User;




public interface UserRepositories  extends JpaRepository<User,Long>{

	Optional<User> findByLogin(String login);
}
