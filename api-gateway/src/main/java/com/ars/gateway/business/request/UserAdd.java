package com.ars.gateway.business.request;


public record UserAdd(
		String firstName,String lastName,
		String login,String password) {

}
