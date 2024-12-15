package com.lucky.art.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lucky.art.externalModel.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"cart"})
public class CartItem {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)  //fetch = FetchType.LAZY
//    @JsonIgnore
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;
    @Transient
    private Product product;  //external
    private Long productId;
    private String size;
    private int quantity = 1;
    private int mrpPrice;
    private int sellingPrice;
    private long userId;

//    ----------------- to fix the error
@Override
public int hashCode() {
    return Objects.hash(id); // Use only the ID
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id); // Use only the ID
    }

}
