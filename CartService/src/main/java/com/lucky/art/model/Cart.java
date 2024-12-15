package com.lucky.art.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Cart {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient
    private User user;
    private Long userId;

//    private Long userId;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // fetch = FetchType.LAZY
//    @JsonIgnore
    @JsonManagedReference
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;
    private int totalItems;
    private double totalMrpPrice;
    private int discount;
    private String couponCode;

//    -------------------- to fix the problem
    @Override
    public int hashCode() {
        return Objects.hash(id); // Use only the ID
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id); // Use only the ID
    }

}
