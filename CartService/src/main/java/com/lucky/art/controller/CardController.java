package com.lucky.art.controller;

import com.lucky.art.exception.ProductException;
import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.ProductService;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.Cart;
import com.lucky.art.model.CartItem;
import com.lucky.art.request.AddItemRequest;
import com.lucky.art.response.ApiResponse;
import com.lucky.art.service.CartItemService;
import com.lucky.art.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CardController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;
    @PostMapping("/createCart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart){
        Cart newCart = cartService.createCart(cart);

        System.out.println(cart.getUser().getEmail());
       return new ResponseEntity<>(newCart,HttpStatus.CREATED );
    }
//    ------------------------------- internal service methods -------------------------------------

    @GetMapping()
    public ResponseEntity<Cart> findUserCartHandler(
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.getUserFromJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception, ProductException {
        User user = userService.getUserFromJwtToken(jwt);
        Product product = productService.getProductById(req.getProductId());

        System.out.println(product.getId());

        CartItem item = cartService.addCrtItem(user,
                product,req.getSize(),req.getQuantity());

//        System.out.println(item.getMrpPrice()+" "+ item.getId());

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart successfully");

        return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.getUserFromJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(),cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item removed from the cart");

        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.getUserFromJwtToken(jwt);

        CartItem updatedCartItem = null;
        if (cartItem.getQuantity()>0){
            updatedCartItem = cartItemService.updateCartItem(user.getId(),
                    cartItemId,cartItem);
        }

        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }



}
