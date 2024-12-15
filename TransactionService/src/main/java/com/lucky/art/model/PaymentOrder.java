package com.lucky.art.model;

import com.lucky.art.domain.PaymentMethod;
import com.lucky.art.domain.PaymentOrderStatus;
import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long amount;
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;
    private PaymentMethod paymentMethod;
    private String paymentLinkId;
    @Transient
    private User user;
    private Long userId;
    @Transient
    private Set<Orders> orders = new HashSet<>();
    private Set<Long> orderId = new HashSet<>();

}
