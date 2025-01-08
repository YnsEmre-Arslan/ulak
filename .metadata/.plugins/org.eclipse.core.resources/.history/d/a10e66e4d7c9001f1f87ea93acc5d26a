package com.ars.user.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name="users")
public class User{

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	private Integer id;
    
	@Column(name = "name" , nullable = false)
    private String name;
	
	@Column(name = "surname" , nullable = false)
    private String surname;

	@Column(name = "status" , nullable = false)
	private String status;

	@Column(name = "host" , nullable = false)
    private String host;
    
	@Column(name = "port" , nullable = false)
	private String port;
	
	@Column(name = "email" , nullable = false)
    private String email;
	
	@Column(name = "password" , nullable = false)
    private String password;

	@Column(name = "username" , nullable = false)
    private String username;
	
	@Column(name = "phone" , nullable = false)
    private String phone;

	@Column(name = "profilePictureUrl" , nullable = false)
    private String profilePictureUrl;
	
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Notification notification;
}
