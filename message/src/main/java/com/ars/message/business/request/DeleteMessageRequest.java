package com.ars.message.business.request;

public record DeleteMessageRequest(String messageId, String host, String username, String password) {

}
