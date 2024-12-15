package com.lucky.art.repository;

import com.lucky.art.domain.AccountStatus;
import com.lucky.art.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByEmail(String email);
   List<Seller> findByAccountStatus(AccountStatus status);
}
