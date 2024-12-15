package com.lucky.art.service.impl;

import com.lucky.art.domain.PaymentOrderStatus;
import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.PaymentStatus;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.OrderService;
import com.lucky.art.model.PaymentOrder;
import com.lucky.art.repository.PaymentOrderRepository;
import com.lucky.art.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentOrderRepository paymentOrderRepository;
    private final OrderService orderService;
    private String apiKey = "apiKay";
    private String apiSecrete = "apiSecrete";
    @Override
    public PaymentOrder createOrder(User user, Set<Orders> orders) {
        Long amount = orders.stream().mapToLong(Orders::getTotalSellingPrice).sum();

        PaymentOrder paymentOrder  = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setUserId(user.getId());
        paymentOrder.setOrders(orders);

        Set<Long> orderIds = new HashSet<>();
        for (Orders order: orders){
            orderIds.add(order.getId());
        }
        paymentOrder.setOrderId(orderIds);

        return null;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
        return paymentOrderRepository.findById(orderId).orElseThrow(
                ()-> new Exception("payment Order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(orderId);

        if (paymentOrder==null){
            throw new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder updatePaymentOrder(PaymentOrder paymentOrder) {

        return null;
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,
                                       String paymentId,
                                       String paymentLinkId) throws Exception {
        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecrete);
            Payment payment = razorpay.payments.fetch(paymentId);
            String status=payment.get("status");
            if (status.equals("captured")){
                Set<Orders> orders = paymentOrder.getOrders();
//                Set<Long> ordersId = paymentOrder.getOrderId();

                for (Orders order: orders){
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                    orderService.saveOrderHandler(order.getId(),order);
                }
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
            paymentOrder.setStatus(PaymentOrderStatus.FAILED);
            paymentOrderRepository.save(paymentOrder);
            return false;
        }
        return false;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException {
        amount = amount*100;    //becouse in razorpay we needs to provide amount in paisa.
        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecrete);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notidy = new JSONObject();
            notidy.put("email",true);
            paymentLinkRequest.put("notify",notidy);

            paymentLinkRequest.put("callback_url",
                    "http://localhost:8086/payment-success/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkUrl = paymentLink.get("short_url");
            String paymentLinkId = paymentLink.get("id");

            return paymentLink;
        }catch (Exception e){
            System.out.println(e);
            throw new RazorpayException(e.getMessage());
        }
    }
}
