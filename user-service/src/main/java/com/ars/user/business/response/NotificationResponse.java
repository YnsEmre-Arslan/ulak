package com.ars.user.business.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
	    
    private Boolean message;
	
    private Boolean mail;
	
    private Boolean news;
	
    private Boolean app;
}
