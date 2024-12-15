package com.lucky.art.externalService;

import com.lucky.art.externalModel.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CART-SERVICE", url = "http://localhost:8083")
public interface CartService {

//    public Cart
    @PostMapping("api/cart/createCart")
    public Cart createCart(@RequestBody Cart cart);
}
