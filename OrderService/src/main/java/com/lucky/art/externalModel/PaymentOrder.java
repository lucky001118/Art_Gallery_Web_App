package com.lucky.art.externalModel;

import com.lucky.art.externalModel.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {
    private Long Id;
    private Long amount;
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;
    private PaymentMethod paymentMethod;
    private String paymentLinkId;
    private User user;
    private Long userId;
    private Set<Orders> orders = new HashSet<>();
    private Set<Long> orderId = new HashSet<>();

}
