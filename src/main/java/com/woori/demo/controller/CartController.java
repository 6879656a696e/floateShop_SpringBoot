package com.woori.demo.controller;

import com.woori.demo.domain.CartItem;
import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import com.woori.demo.service.CartService;
import com.woori.demo.service.ProductService;
import com.woori.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/addCart")
    @ResponseBody
    public void addCart(Long productKey, Long userKey){
        User user=userService.findUser(userKey);
        Product product=productService.findProduct(productKey);
        int count=1;
        cartService.addCart(product, user, count);
    }

    @GetMapping("/getCartList")
    @ResponseBody
    public List<CartItem> getCartList(@RequestParam  Long userKey){
        return cartService.getCartList(userKey);
    }
}
