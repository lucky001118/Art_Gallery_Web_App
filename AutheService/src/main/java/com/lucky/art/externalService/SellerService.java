package com.lucky.art.externalService;

import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalModel.VerificationCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SELLER-SERVICE", url = "http://localhost:8085")
public interface SellerService {
    @PostMapping("seller/createUser")
    public Seller createSeller(@RequestBody Seller seller) throws Exception;

    @GetMapping("seller/findSeller/{email}")
    public Seller findSellerByEmail(@PathVariable String email) throws Exception ;

//    this is for the verificationCode class
    @GetMapping("/verification/code/{email}")
    public VerificationCode findCodeByEmail(@PathVariable String email);

    @DeleteMapping("/verification/deleteCode/{codeEmail}")
    public void deleteVerificationCode(@PathVariable("codeEmail") String codeEmail);

    @PostMapping("/verification")
    public void saveVerificationCode(@RequestBody VerificationCode code);

}
