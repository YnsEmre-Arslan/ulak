package com.ars.gateway.business.dto;

import lombok.Builder;

@Builder
public record CredentialsDto(String login, char[] password) {

}
