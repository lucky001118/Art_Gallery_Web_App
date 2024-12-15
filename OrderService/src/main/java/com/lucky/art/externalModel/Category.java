package com.lucky.art.externalModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;


    private String categoryId;

    private Category parentCategory;

    private Integer level;
}
