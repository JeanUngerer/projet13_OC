package com.yourcaryourway.chatappback.dtos.requests;

import lombok.*;


public class RefreshTokenRequest {
    String token;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
