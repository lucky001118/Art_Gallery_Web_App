package com.lucky.art.service.impl;

import com.lucky.art.domain.OrderStatus;
import com.lucky.art.domain.PaymentStatus;
import com.lucky.art.externalModel.Address;
import com.lucky.art.externalModel.Cart;
import com.lucky.art.externalModel.CartItem;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.OrderItem;
import com.lucky.art.model.Orders;
import com.lucky.art.repository.OrderItemRepository;
import com.lucky.art.repository.OrderRepository;
import com.lucky.art.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;
    @Override
    public Set<Orders> createOrder(User user, Address shippingAddress, Cart cart) {

        if (!user.getAddresses().contains(shippingAddress)){
            user.getAddresses().add(shippingAddress);
        }
        Address address = userService.saveAddressOfOrder(shippingAddress);

        Map<Long,List<CartItem>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item->item.getProduct().getSeller().getId()));

        Set<Orders> orders = new HashSet<>();
        for (Map.Entry<Long, List<CartItem>> entry: itemsBySeller.entrySet()){
            Long sellerId = entry.getKey();
            List<CartItem> items = entry.getValue();

            int totalOrderPrice = items.stream().mapToInt(
                    CartItem::getSellingPrice
            ).sum();
            int totalItem = items.stream().mapToInt(CartItem::getQuantity).sum();

            Orders createdOrder = new Orders();
            createdOrder.setUser(user);
            createdOrder.setUserId(user.getId());
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setShippingAddress(address);
            createdOrder.setShippingAddressId(address.getId());
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Orders saveOrder = orderRepository.save(createdOrder);
            orders.add(saveOrder);

            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem item: items){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(saveOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setProduct(item.getProduct());
                orderItem.setProductId(item.getProduct().getId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(item.getUserId());
                orderItem.setSellingPrice(item.getSellingPrice());

                saveOrder.getOrderItems().add(orderItem);

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                orderItems.add(savedOrderItem);
            }
        }
        return orders;
    }

    @Override
    public Orders findOrdersById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(()->new Exception("Order not found.."));
    }

    @Override
    public List<Orders> usersOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Orders> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Orders updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        Orders order = findOrdersById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Orders cancelOrder(Long orderId, User user) throws Exception {
        Orders order = findOrdersById(orderId);

        if (!user.getId().equals(order.getUserId())){
            throw new Exception("You do not have access this order");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem getOrderItemById(Long id) throws Exception {
        return orderItemRepository.findById(id).orElseThrow(
                ()->new Exception("Order item not found by provided id or not exist"));
    }
}
