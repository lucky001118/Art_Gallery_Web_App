package com.lucky.art.externalModel;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;
    private String name;
    private String locality;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String mobile;
}
