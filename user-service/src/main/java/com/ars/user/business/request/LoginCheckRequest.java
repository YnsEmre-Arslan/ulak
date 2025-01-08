package com.ars.user.business.request;

import lombok.Builder;

@Builder
public record LoginCheckRequest(String name, String code,String to,String template) {
	
    public LoginCheckRequest(String name, String code, String to) {
        this(name, code, to, "tenplate-1"); 
    }

}
