package com.lucky.art.externalService;

import com.lucky.art.externalModel.Seller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SELLER-SERVICE",url = "http://localhost:8085")
public interface SellerService {
    @GetMapping("/seller/profile")
    public Seller getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception;
}
