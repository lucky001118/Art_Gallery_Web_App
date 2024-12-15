package com.lucky.art.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucky.art.externalModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String reviewText;
    @Column(nullable = false)
    private double rating;
    @ElementCollection
    private List<String> productImages;
    @JsonIgnore
    @ManyToOne
    private Product product;   //internal
    @Transient
    private User user;  //external
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
