package com.lucky.art.externalModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
