package com.lucky.art.externalModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long Id;
    private String title;
    private String description;
    private int mrpPrice;
    private int discountPercentage;
    private int quantity;
    private String color;
    private List<String> images = new ArrayList<>();
    private int numRatings;
    private Category category;  //internal
    private Seller seller;  //external
    private LocalDateTime createdAt;
    private String sizes;
    private List<Review> reviews = new ArrayList<>();  //internal

}
