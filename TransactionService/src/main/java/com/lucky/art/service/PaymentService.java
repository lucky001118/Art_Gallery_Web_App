package com.lucky.art.service;
import com.razorpay.PaymentLink;
import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.User;
import com.lucky.art.model.PaymentOrder;
import com.razorpay.RazorpayException;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Orders> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;
    PaymentOrder updatePaymentOrder(PaymentOrder paymentOrder);
    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,String paymentId,String paymentLinkId) throws Exception;
    PaymentLink createRazorpayPaymentLink(User user,Long amount,Long orderId) throws RazorpayException;


}
