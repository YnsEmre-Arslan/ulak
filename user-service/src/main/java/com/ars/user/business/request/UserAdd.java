package com.ars.user.business.request;

import lombok.Builder;

@Builder
public record UserAdd(String firstName, String lastName, String login, String password) {
}

