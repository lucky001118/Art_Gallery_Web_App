package com.lucky.art.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucky.art.externalModel.Product;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @JsonIgnore
    @ManyToOne
    private Orders order;
    @Transient
    private Product product;  //external model from product
    private Long productId;
    private String size;
    private int quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;
    private Long userId;

}
