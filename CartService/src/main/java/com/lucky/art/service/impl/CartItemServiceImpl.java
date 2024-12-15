package com.lucky.art.service.impl;

import com.lucky.art.exception.ProductException;
import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.ProductService;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.CartItem;
import com.lucky.art.repository.CartItemRepository;
import com.lucky.art.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;
    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws ProductException,Exception {
        CartItem item = findCartItemById(id);
        Product product = productService.getProductById(item.getProductId());
        item.setProduct(product);

        User user = userService.getUserById(item.getCart().getUserId());
        item.getCart().setUser(user);
        User cartItemUser=item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("you can not update this cart item");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);
        User user = userService.getUserById(item.getCart().getUserId());
        item.getCart().setUser(user);
//        System.out.println(user.getId()+" "+ user.getId().equals(userId));
        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(item);
        }else{
            throw new Exception("You can't delete this item");
        }
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {

        return cartItemRepository.findById(id).orElseThrow(
                ()-> new Exception("cart item not found with id "+id));
    }
}
