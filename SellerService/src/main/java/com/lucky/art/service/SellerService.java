package com.lucky.art.service;

import com.lucky.art.domain.AccountStatus;
import com.lucky.art.model.Seller;

import java.util.List;

public interface SellerService {

//    This is for external service usage
    public Seller createSeller(Seller seller) throws Exception;
    public Seller findByEmailId(String email) throws Exception;

//    ------------------------------------------------

//    This is for internal seller service usage
    Seller getSellProfile(String jwt) throws Exception;
//    Seller createSeller(Seller seller);  ---> this create seller is also decleared in the external services
    Seller getSellerById(Long id) throws Exception;
//    getSellerByEmail is also defines as --> findByEmailId(String email) in external service usage section
    List<Seller> getAllSellers(AccountStatus status) throws Exception;
    Seller updateSeller(Long id,Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email,String otp) throws Exception;
    Seller updateSellerAccountStatus(Long id,AccountStatus status) throws Exception;

}
