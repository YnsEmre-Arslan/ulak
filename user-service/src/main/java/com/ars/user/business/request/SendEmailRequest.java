package com.ars.user.business.request;

import lombok.Builder;

@Builder
public record SendEmailRequest(
		
	     String host,     // SMTP sunucusu
	     int port,     // SMTP portu
	     String from,      // Gönderen e-posta adresi
	     String password,  // Gönderen hesabının şifresi
	     String to,        // Alıcı e-posta adresi
	     String body    // E-posta içeriği
		)
{

}
