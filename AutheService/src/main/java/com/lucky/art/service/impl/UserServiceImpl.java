package com.lucky.art.service.impl;

import com.lucky.art.config.JwtProvider;
import com.lucky.art.model.User;
import com.lucky.art.repository.UserRepository;
import com.lucky.art.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = this.findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user==null){
            throw new Exception("user not found with email -- "+email);
        }
        return user;
    }
}
