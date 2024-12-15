package com.lucky.art.controller;

import com.lucky.art.exception.SellerException;
import com.lucky.art.externalModel.OrderStatus;
import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalService.OrderService;
import com.lucky.art.model.Seller;
import com.lucky.art.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/order")
public class SellerOrderController {
    private final SellerService sellerService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Orders>> gelAllOrdersHandler(
            @RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellProfile(jwt);
        List<Orders> orders = orderService.getSellerOrders(seller.getId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Orders> updateOrderHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @PathVariable OrderStatus orderStatus) throws Exception{
        Orders orders = orderService.updateOrderStatus(orderId,orderStatus);

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }
}
