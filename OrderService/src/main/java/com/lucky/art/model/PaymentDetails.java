package com.lucky.art.model;

import com.lucky.art.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkRefrenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentIdZWSP;
    private PaymentStatus status;
}
