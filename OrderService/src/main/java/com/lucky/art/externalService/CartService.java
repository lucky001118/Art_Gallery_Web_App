package com.lucky.art.externalService;

import com.lucky.art.externalModel.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CART-SERVICE",url = "http://localhost:8083")
public interface CartService {
    @GetMapping("/api/cart")
    public Cart findUserCartHandler(
            @RequestHeader("Authorization") String jwt) throws Exception;
}
