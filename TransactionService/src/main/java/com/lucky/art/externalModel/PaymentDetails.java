package com.lucky.art.externalModel;

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
