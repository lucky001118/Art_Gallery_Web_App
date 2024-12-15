package com.lucky.art.service.impl;

import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.Cart;
import com.lucky.art.model.CartItem;
import com.lucky.art.repository.CartItemRepository;
import com.lucky.art.repository.CartRepository;
import com.lucky.art.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    @Override
    public Cart createCart(Cart cart) {
        Cart cart1 = new Cart();
        cart1.setUserId(cart.getUserId());
        cart1.setUser(cart.getUser());
        return cartRepository.save(cart1);
    }

    @Override
    public CartItem addCrtItem(User user, Product product, String size, int quantity) throws Exception {


        Cart cart = findUserCart(user);
//        cartRepository.save(cart);

        CartItem isPresent = cartItemRepository.findByCartAndProductIdAndSize(
                cart,product.getId(),size);

//        System.out.println(isPresent.getProductId());

        if (isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setProductId(product.getId());
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPriceForCartItem = quantity*product.getSellingPrice();
            cartItem.setSellingPrice(totalPriceForCartItem);
            cartItem.setMrpPrice(quantity*product.getMrpPrice());

            System.out.println(totalPriceForCartItem);

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);    //we have to add this point but this is creating stackoverflow error

//            System.out.println("userId from the user "+cart.getUser().getId());
//            cartItem.getCart().setUser(cart.getUser());
//            cartItem.getCart().setUserId(cart.getUser().getId());
//            cartItem.getCart().setTotalItems(cart.getTotalItems());
//            cartItem.getCart().setCartItems(cart.getCartItems());
//            cartItem.getCart().setDiscount(cart.getDiscount());
//            cartItem.getCart().setTotalSellingPrice(cart.getTotalSellingPrice());
//            cartItem.getCart().setTotalMrpPrice(cart.getTotalMrpPrice());
//            cartItem.getCart().setCouponCode(cart.getCouponCode());

            return cartItemRepository.save(cartItem);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) throws Exception {
        Cart cart = cartRepository.findByUserId(user.getId());
        User user1 = userService.getUserById(user.getId());
        cart.setUser(user1);
        cart.getUser().setAddresses(user1.getAddresses());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem: cart.getCartItems()){
            totalPrice+=cartItem.getMrpPrice();
            totalDiscountedPrice+=cartItem.getSellingPrice();
            totalItem+=cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItems(totalItem);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountedPrice));

        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice<=0){
            return 0;
        }
        double discount = mrpPrice-sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;
        return (int)discountPercentage;
    }
}
