package com.lucky.art.externalService;

import com.lucky.art.externalModel.Address;
import com.lucky.art.request.LoginOtpRequest;
import com.lucky.art.request.LoginRequest;
import com.lucky.art.response.ApiResponse;
import com.lucky.art.response.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AUTH-SERVICE", url = "http://localhost:8082")
public interface UserService {
    @GetMapping("/getEmail")
    public String getEmailFromJwtToken(
            @RequestHeader("Authorization")String jwt);

    @PostMapping("address/save")
    public Address saveAddressOfSeller(@RequestBody Address address);

    @PostMapping("auth/pass")
    public String EncodeSellerPass(@RequestBody String pass);

    @PostMapping("/loginSeller")
    public AuthResponse loginSellr(@RequestBody LoginRequest req);

//    public String getOtpForSellerLogin(@PathVariable String email);

    @PostMapping("/sent/login-signup-otp")
    public ApiResponse sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception;

    @GetMapping("/address/{id}")
    public Address findAddressbyId(@PathVariable Long id) throws Exception;

    @PutMapping("/address/{id}")
    public Address updateAddressbyId(@PathVariable Long id,@RequestBody Address add) throws Exception;

    @DeleteMapping("/address/{id}")
    public void deleteAddressbyId(@PathVariable Long id) throws Exception;
}
