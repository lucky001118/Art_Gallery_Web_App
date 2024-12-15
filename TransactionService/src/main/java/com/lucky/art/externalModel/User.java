package com.lucky.art.externalModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long Id;
    private String fullName;
    private String email;
    private String password;
    private String mobile;
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;
    private Set<Address> addresses = new HashSet<>();
    private Set<Coupon> usedCoupons = new HashSet<>();
    private String profilePic;
}
