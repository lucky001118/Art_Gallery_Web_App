package com.lucky.art.externalModel;

import com.lucky.art.externalModel.Address;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Long Id;
    private String orderId;
    private User user;
    private Long sellerId;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Address shippingAddress;
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
