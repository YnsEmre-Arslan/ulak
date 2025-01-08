package com.ars.user.business.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSettinResponse {
	
	
    private String imageUrl;
	
    private String username;
	
    private String statusText;

    private String name;
    
    private String surname;

    private String host;
    
    private String mailAddress;
    
    private String mailPassword;

}
