package com.ars.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ars.user.entities.Contac;

import jakarta.transaction.Transactional;

public interface ContacRepository extends JpaRepository<Contac, Integer> {
	
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Contac u WHERE u.userContact = :userContact AND u.mailAddress = :mailAddress")
	boolean existsByUserContactAndMailAddress(String userContact, String mailAddress);
	
    List<Contac> findByUserContact(String userContact);
    
    
    // mailAddress'e göre sorgu yapan metod
    Optional<Contac> findByMailAddress(String mailAddress);
    
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Contac c WHERE c.userContact = :userContact AND c.mailAddress = :mailAddress")
    void deleteByUserContactAndMailAddress(String userContact, String mailAddress);


    
    @Query("SELECT c FROM Contac c WHERE c.userContact = :userContact AND c.mailAddress = :mailAddress")
    Contac findByUserContactAndMailAddress(String userContact, String mailAddress);

 
}
