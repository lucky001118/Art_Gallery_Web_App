package com.lucky.art.externalModel;

import com.lucky.art.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long Id;

    private User user;
    private Long userId;
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;
    private int totalItems;
    private double totalMrpPrice;
    private int discount;
    private String couponCode;
}
