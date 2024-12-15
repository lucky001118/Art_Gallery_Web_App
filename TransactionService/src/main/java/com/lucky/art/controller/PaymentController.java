package com.lucky.art.controller;

import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalModel.SellerReport;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.OrderService;
import com.lucky.art.externalService.SellerService;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.PaymentOrder;
import com.lucky.art.repository.PaymentOrderRepository;
import com.lucky.art.response.ApiResponse;
import com.lucky.art.response.PaymentLinkResponse;
import com.lucky.art.service.PaymentService;
import com.lucky.art.service.TransactionService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final OrderService orderService;
    private final PaymentOrderRepository paymentOrderRepository;
    private final TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt ) throws Exception{
        User user = userService.getUserFromJwtToken(jwt);

        PaymentLinkResponse paymentLinkResponse;
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.ProceedPaymentOrder(
                paymentOrder,
                paymentId,
                paymentLinkId);

        if (paymentSuccess){
            for (Orders order: paymentOrder.getOrders()){
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders()+1);
                report.setTotalEarnings(report.getTotalEarnings()+order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
                sellerService.updateSellerReport(report);
            }
        }
        ApiResponse res = new ApiResponse();
        res.setMessage("Payment Successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // ---------------- external handler -----------

    @PostMapping("/{paymentOrderId}/amount/{paymentAmount}")
    public ResponseEntity<PaymentLink> makePaymentLink(
            @PathVariable Long paymentOrderId,
            @PathVariable Long paymentAmount,
            @RequestBody User user) throws RazorpayException {
        PaymentLink link = paymentService.createRazorpayPaymentLink(user,paymentAmount,paymentOrderId);
        return new ResponseEntity<>(link,HttpStatus.CREATED);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PaymentOrder> createOrder(@PathVariable Long userId,
                                                    @RequestBody Set<Orders> orders) throws Exception {
        User user = userService.getUserById(userId);
        PaymentOrder paymentOrder = paymentService.createOrder(user,orders);
        return  new ResponseEntity<>(paymentOrder,HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<PaymentOrder> savePaymentOrder(
        @RequestBody PaymentOrder paymentOrder){

        PaymentOrder order = paymentOrderRepository.save(paymentOrder);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }
}
