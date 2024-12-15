package com.lucky.art.repository;

import com.lucky.art.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {
    PaymentOrder findByPaymentLinkId(String paymentLinkId);
}
