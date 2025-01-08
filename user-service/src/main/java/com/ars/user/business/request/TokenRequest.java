package com.ars.user.business.request;

import lombok.Builder;

@Builder
public record TokenRequest(String token) {

}
