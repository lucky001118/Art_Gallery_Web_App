package com.lucky.art.service.impl;

import com.lucky.art.domain.USER_ROLE;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalService.SellerService;
import com.lucky.art.model.User;
import com.lucky.art.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomeUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private static final String SELLER_PREFIX="seller_";
    private final SellerService sellerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith(SELLER_PREFIX)){
            String actualUsername = username.substring(SELLER_PREFIX.length());
//            String email = actualUsername;
            Seller seller = null;
            try {
                seller = sellerService.findSellerByEmail(actualUsername);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (seller!=null){
                return  buildUserDetails(seller.getEmail(),seller.getPassword(),USER_ROLE.ROLE_SELLER);
            }
        }else {
            User user = userRepository.findByEmail(username);
            if (user!=null){
                return buildUserDetails(user.getEmail(),user.getPassword(),user.getRole());
            }
        }
        throw new UsernameNotFoundException("User or seller not found with email "+username);
    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
        if (role==null) role=USER_ROLE.ROLE_CUSTOMER;

        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(email
                ,password,authoritiesList);
    }
}
