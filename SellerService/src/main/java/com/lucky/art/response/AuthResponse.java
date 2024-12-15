package com.lucky.art.response;

import  com.lucky.art.externalModel.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwtToken;
    private String message;
    private USER_ROLE role;
}
