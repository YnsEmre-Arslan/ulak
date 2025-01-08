package com.ars.user.business.request;

import lombok.Builder;

@Builder
public record UpdateMessageRequest(String messageId, String host, String username, String password,String newText) {

}
