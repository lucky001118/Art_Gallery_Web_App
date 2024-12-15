package com.lucky.art.externalService;

import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalModel.SellerReport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SELLER-SERVICE",url = "http://localhost:8085")
public interface SellerService {
    @GetMapping("/seller/profile")
    public Seller getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception;

    @GetMapping("/seller/{id}")
    public Seller getSellerById(@PathVariable Long id) throws Exception;

    @GetMapping("/seller/sellerReport")
    public SellerReport getSellerReport(@RequestParam Seller seller);

    @PutMapping("/seller/update/sellerReport")
    public SellerReport updateSellerReport(@RequestBody SellerReport sellerReport);

    @GetMapping("/seller/report")
    public SellerReport getSellerReport(
            @RequestHeader("Authorization") String jwt) throws Exception;
}
