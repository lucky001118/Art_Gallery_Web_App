package com.lucky.art.model;

import com.lucky.art.domain.OrderStatus;
import com.lucky.art.domain.PaymentStatus;
import com.lucky.art.externalModel.Address;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String orderId;
    @Transient
    private User user;
    private Long userId;
    private Long sellerId;
    @OneToMany         //(mappedBy = "orders",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    @Transient
    private Address shippingAddress;   //external service  --> should have @Transient annotation
    private Long shippingAddressId;
    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();
    private double totalMrpPrice;
    private Integer totalSellingPrice;
    private Integer discount;
    private OrderStatus orderStatus;
    private int totalItem;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private LocalDateTime orderDate = LocalDateTime.now();
    private LocalDateTime deliverDate = orderDate.plusDays(7);
}
