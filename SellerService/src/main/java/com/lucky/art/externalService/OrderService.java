package com.lucky.art.externalService;

import com.lucky.art.externalModel.OrderStatus;
import com.lucky.art.externalModel.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ORDER-SERVICE", url = "http://localhost:8087")
public interface OrderService {

    @GetMapping("/api/orders/sellerOrders")
    public List<Orders> getSellerOrders(@PathVariable Long sellerId);

    @PatchMapping("/api/orders/{orderId}/status/{orderStatus}")
    public Orders updateOrderStatus(@PathVariable Long orderId,
                                                    @PathVariable OrderStatus orderStatus) throws Exception;
}
