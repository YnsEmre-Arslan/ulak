package com.ars.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	private Integer id;
    
	@Column(name = "message" , nullable = false)
    private Boolean message;
	
	@Column(name = "mail" , nullable = false)
    private Boolean mail;
	
	@Column(name = "news" , nullable = false)
    private Boolean news;
	
	@Column(name = "app" , nullable = false)
    private Boolean app;

    @OneToOne
    @JoinColumn(name = "user_id")  // User ile olan ilişkiyi user_id kolonu ile kuruyoruz
    private User user;
	
}
