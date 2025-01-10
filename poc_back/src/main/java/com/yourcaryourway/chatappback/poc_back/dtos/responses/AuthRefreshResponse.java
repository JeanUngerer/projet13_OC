package com.yourcaryourway.chatappback.poc_back.dtos.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AuthRefreshResponse {
    String token;
}
