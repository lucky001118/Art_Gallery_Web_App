package com.lucky.art.model;

import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Transient
    private User user;  //external entity from the userService/authService
    private Long userId;
    @Transient
    private Set<Product> products = new HashSet<>();
    private Set<Long> productsIds = new HashSet<>();
}
