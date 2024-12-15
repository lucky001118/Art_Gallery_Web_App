package com.lucky.art.controller;

import com.lucky.art.domain.USER_ROLE;
import com.lucky.art.repository.UserRepository;
import com.lucky.art.request.LoginOtpRequest;
import com.lucky.art.request.LoginRequest;
import com.lucky.art.request.SignupRequest;
import com.lucky.art.response.ApiResponse;
import com.lucky.art.response.AuthResponse;
import com.lucky.art.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        String jwt = authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setJwtToken(jwt);
        res.setMessage("Register Success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(),req.getRole());
        ApiResponse res = new ApiResponse();
        res.setMessage("otp set successfully");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("auth/pass")
    public ResponseEntity<String> EncodeSellerPass(@RequestBody String pass){
        String encodePassword = passwordEncoder.encode(pass);
        return new ResponseEntity<>(encodePassword, HttpStatus.ACCEPTED);
    }
}
