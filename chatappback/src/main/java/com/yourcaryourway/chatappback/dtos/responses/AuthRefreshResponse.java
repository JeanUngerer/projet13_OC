package com.yourcaryourway.chatappback.dtos.responses;

import lombok.*;


public class AuthRefreshResponse {
    String token;

    public AuthRefreshResponse(String token) {
        this.token = token;
    }

    public AuthRefreshResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthRefreshResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
