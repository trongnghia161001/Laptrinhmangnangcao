package com.laptrinhmangnangcao.ltm.dto.response;

import lombok.Data;
@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;

    public LoginResponse(String accessToken, Long id) {
        this.accessToken = accessToken;
        this.id = id;
    }
}
