package com.yourcaryourway.chatappback.poc_back.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RefreshTokenRequest {
    String token;
}
