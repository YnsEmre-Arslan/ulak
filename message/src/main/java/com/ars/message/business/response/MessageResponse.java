package com.ars.message.business.response;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
	

    private String id;

    private String text;

    private Boolean sent;
	
    private Date time;
    
    private Date date;

    private Boolean isEdit;
    

}
