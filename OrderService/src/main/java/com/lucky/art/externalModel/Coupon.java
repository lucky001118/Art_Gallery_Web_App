package com.lucky.art.externalModel;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    private Long id;
    private String code;
    private String discountPercentage;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
    private double minimumOrderValue;
    private boolean isActive = false;
    private Set<User> usedByUsers = new HashSet<>();

}
