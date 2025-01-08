package com.ars.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="contact")
public class Contac {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	private Integer id;
	
	@Column(name = "user_contact" , nullable = false)
    private String userContact;
    
	@Column(name = "name" , nullable = false)
    private String name;
	
	@Column(name = "surname" , nullable = false)
    private String surname;

	@Column(name = "mail_address" , nullable = false)
    private String mailAddress;
	
	@Column(name = "image_url" , nullable = false)
    private String imageUrl;

	

}
 