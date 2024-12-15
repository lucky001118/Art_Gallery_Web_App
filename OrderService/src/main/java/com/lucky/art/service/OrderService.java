package com.lucky.art.service;

import com.lucky.art.domain.OrderStatus;
import com.lucky.art.externalModel.Address;
import com.lucky.art.externalModel.Cart;

import com.lucky.art.externalModel.User;
import com.lucky.art.model.OrderItem;
import com.lucky.art.model.Orders;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Set<Orders> createOrder(User user, Address shippingAddress, Cart cart);
    Orders findOrdersById(Long id) throws Exception;
    List<Orders> usersOrderHistory(Long userId);
    List<Orders> sellersOrder(Long sellerId);
    Orders updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
    Orders cancelOrder(Long orderId,User user) throws Exception;
    OrderItem getOrderItemById(Long id) throws Exception;

}
