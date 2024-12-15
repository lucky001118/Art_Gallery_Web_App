package com.lucky.art.controller;

import com.lucky.art.externalModel.*;
import com.lucky.art.externalService.CartService;
import com.lucky.art.externalService.PaymentService;
import com.lucky.art.externalService.SellerService;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.OrderItem;
import com.lucky.art.model.Orders;
import com.lucky.art.domain.OrderStatus;
import com.lucky.art.repository.OrderRepository;
import com.lucky.art.response.PaymentLinkResponse;
import com.lucky.art.service.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final SellerService sellerService;
    private final CartService cartService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt) throws Exception, RazorpayException {

        User user = userService.getUserFromJwtToken(jwt);
        Cart cart = cartService.findUserCartHandler(jwt);
        Set<Orders> orders = orderService.createOrder(user,shippingAddress,cart);

        PaymentOrder paymentOrder = paymentService.createOrder(user.getId(),orders);

        PaymentLinkResponse res = new PaymentLinkResponse();

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink payment = paymentService.makePaymentLink(
                    paymentOrder.getId(),
                    paymentOrder.getAmount(),
                    user).getBody();
            String paymentUrl = payment.get("short_url");
            String paymentUrlId = payment.get("id");

            res.setPayment_link_url(paymentUrl);

            paymentOrder.setPaymentLinkId(paymentUrlId);
            paymentService.savePaymentOrder(paymentOrder);
        }
//        we are not using the Stipe payment hance we are not making condition for make payment in strip api
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Orders>> usersOrderHistoryHandler(
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.getUserFromJwtToken(jwt);
        List<Orders> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long orderId,
                                               @RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.getUserFromJwtToken(jwt);
        Orders orders =orderService.findOrdersById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId,
                                                      @RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.getUserFromJwtToken(jwt);
        OrderItem orderItem = orderService.getOrderItemById(orderItemId);
        return new ResponseEntity<>(orderItem,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancle")
    public ResponseEntity<Orders> cancelOrder(@PathVariable Long orderId,
                                              @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.getUserFromJwtToken(jwt);
        Orders order = orderService.cancelOrder(orderId,user);

        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport report = sellerService.getSellerReport(seller);

        report.setCanceledOrders(report.getCanceledOrders()+1);
        report.setTotalRefund(report.getTotalRefund()+order.getTotalSellingPrice());
        sellerService.updateSellerReport(report);

        return ResponseEntity.ok(order);
    }

//    ------------------  External services -----------------
    @GetMapping("/sellerOrders")
    public ResponseEntity<List<Orders>> getSellerOrders(@PathVariable Long sellerId){
        List<Orders> orders = orderService.sellersOrder(sellerId);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId,
                                                    @PathVariable OrderStatus orderStatus) throws Exception{
        Orders orders = orderService.updateOrderStatus(orderId,orderStatus);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);

    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Orders> saveOrderHandler(@PathVariable Long orderId, @RequestBody Orders orders) throws Exception {
        Orders orders1 = orderService.findOrdersById(orderId);
        Orders saveOrders = orderRepository.save(orders1);
        return new ResponseEntity<>(saveOrders,HttpStatus.ACCEPTED);
    }
}
