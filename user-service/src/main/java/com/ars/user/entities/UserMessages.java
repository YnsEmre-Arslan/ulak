package com.ars.user.entities;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessages {
	
	
    private String id;
    
    private String from;
    
    private String text;
   
    private String Subject;

    private String time;
	
    private String sent;
	
    private Date date;

}
