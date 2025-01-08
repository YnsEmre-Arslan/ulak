package com.ars.message.business.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAccount {
	private String address;
	private String password;
	private String host;
	private int port;

}
