package com.ars.message.business.request;

public record UpdateMessageRequest(String messageId, String host, String username, String password,String newText) {

}
