package com.lucky.art.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateReviewRequest {
    private String reviewText;
    private double reviewRatting;
    private List<String> productImages;
}
