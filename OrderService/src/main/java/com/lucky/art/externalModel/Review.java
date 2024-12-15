package com.lucky.art.externalModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private String reviewText;
    private double rating;
    private List<String> productImages;
    private Product product;   //internal
    private User user;  //external
    private LocalDateTime createdAt = LocalDateTime.now();
}
