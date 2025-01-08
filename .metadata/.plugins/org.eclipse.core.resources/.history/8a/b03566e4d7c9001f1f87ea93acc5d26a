package com.ars.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ars.user.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	

    @Query("SELECT u.email FROM User u WHERE u.username = :username")
    String findEmailByUsername(@Param("username") String username);
    
    User findByUsername(String username);
    
    
    @Query("SELECT u.profilePictureUrl FROM User u WHERE u.email = :email")
    Optional<String> findProfilePictureUrlByEmail(@Param("email") String email);
    
    Optional<User> findByEmail(String email);
    



}
