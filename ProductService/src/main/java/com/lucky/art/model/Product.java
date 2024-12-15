package com.lucky.art.model;

import com.lucky.art.externalModel.Seller;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String title;
    private String description;
    private int mrpPrice;
    private int discountPercentage;
    private int sellingPrice;
    private int quantity;
    private String color;
    @ElementCollection
    private List<String> images = new ArrayList<>();
    private int numRatings;
    @ManyToOne
    private Category category;  //internal
    @Transient
    private Seller seller;  //external
    private Long sellerId;
    private LocalDateTime createdAt;
    private String sizes;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();  //internal

}
