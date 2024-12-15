package com.lucky.art.model;

import com.lucky.art.domain.AccountStatus;
import com.lucky.art.externalModel.Address;
import com.lucky.art.externalModel.USER_ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String sellerName;
    private String mobile;

    @NotNull
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();
    @Embedded
    private BankDetails bankDetails = new BankDetails();
    @Transient
    private Address pickupAddress = new Address();
    private Long pickupAddressId;
    private String GSTIN;
    private USER_ROLE role = USER_ROLE.ROLE_SELLER;
    private boolean isEmailVerified=false;
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
}
