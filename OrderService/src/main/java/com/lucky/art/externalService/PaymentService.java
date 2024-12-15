package com.lucky.art.externalService;

import com.lucky.art.model.Orders;
import com.lucky.art.externalModel.PaymentOrder;
import com.lucky.art.externalModel.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(name = "TRANSACTION-SERVICE",url = "http://localhost:8086")
public interface PaymentService {
    @PostMapping("/api/payment/{paymentOrderId}/amount/{paymentAmount}")
    public ResponseEntity<PaymentLink> makePaymentLink(
            @PathVariable Long paymentOrderId,
            @PathVariable Long paymentAmount,
            @RequestBody User user) throws RazorpayException;

    @PostMapping("/api/payment/{userId}")
    public PaymentOrder createOrder(@PathVariable Long userId,
                                    @RequestBody Set<Orders> orders) throws Exception;

    @PostMapping("/api/payment")
    public PaymentOrder savePaymentOrder(
            @RequestBody PaymentOrder paymentOrder);
}
