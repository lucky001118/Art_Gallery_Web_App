package com.lucky.art.model;

import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Transient
    private Orders order;
    private Long orderId;
    @Transient
    private User customers;
    private Long customerId;
    @Transient
    private Seller seller;
    private Long sellerId;
    private LocalDateTime date = LocalDateTime.now();

}
