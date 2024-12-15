package com.lucky.art.controller;

import com.lucky.art.exception.ProductException;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalService.SellerService;
import com.lucky.art.model.Product;
import com.lucky.art.request.createProductRequest;
import com.lucky.art.service.ProductService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers/products")
@RequiredArgsConstructor
public class SellerProductController {
    private final ProductService productService;
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt) throws Exception ,ProductException{
        Seller seller = sellerService.getSellerByJwt(jwt);
        List<Product> products = productService.getProductBySellerId(seller.getId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody createProductRequest request,
            @RequestHeader("Authorization") String jwt)
    throws ExecutionControl.UserException,
            ProductException, Exception{
        Seller seller = sellerService.getSellerByJwt(jwt);

        Product product = productService.createProduct(request,seller);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        try{
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProductException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                                 @RequestBody Product product){
        try{
            Product updatedProduct = productService.updateProduct(productId,product);
            return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
        }catch (ProductException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
