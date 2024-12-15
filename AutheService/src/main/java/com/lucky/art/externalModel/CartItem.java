package com.lucky.art.externalModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long Id;
    private Cart cart;
    private Product product;  //external
    private String size;
    private int quantity = 1;
    private int mrpPrice;
    private int sellingPrice;
    private long userId;


}
