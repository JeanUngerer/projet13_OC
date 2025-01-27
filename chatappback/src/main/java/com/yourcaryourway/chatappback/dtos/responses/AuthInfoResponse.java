package com.yourcaryourway.chatappback.dtos.responses;

import lombok.*;



@ToString
public class AuthInfoResponse {

    String token;
    String refreshToken;
    Boolean isCustomer;

    public AuthInfoResponse(String token, String refreshToken, Boolean isCustomer) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.isCustomer = isCustomer;
    }

    public AuthInfoResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getCustomer() {
        return isCustomer;
    }

    public void setCustomer(Boolean customer) {
        isCustomer = customer;
    }
}
