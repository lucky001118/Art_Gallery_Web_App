package com.lucky.art.externalModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    private Long Id;
    private String sellerName;
    private String mobile;
    private String email;
    private String password;
    private BusinessDetails businessDetails = new BusinessDetails();
    private BankDetails bankDetails = new BankDetails();
    private Address pickupAddress = new Address();
    private String GSTIN;
    private USER_ROLE role = USER_ROLE.ROLE_SELLER;
    private boolean isEmailVerified=false;
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
}
