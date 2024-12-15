package com.lucky.art.externalService;

import com.lucky.art.exception.ProductException;
import com.lucky.art.externalModel.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE",url = "http://localhost:8084")
public interface ProductService {

    @GetMapping("/products/{productId}")
    public Product getProductById(
            @PathVariable Long productId) throws ProductException;
}
