package com.lucky.art.service;

import com.lucky.art.domain.USER_ROLE;
import com.lucky.art.request.LoginRequest;
import com.lucky.art.request.SignupRequest;
import com.lucky.art.response.AuthResponse;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;
    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    AuthResponse signing(LoginRequest req);
}
