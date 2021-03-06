package com.woori.demo.controller;

import com.woori.demo.domain.CartItem;
import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import com.woori.demo.service.CartService;
import com.woori.demo.service.ProductService;
import com.woori.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


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

    @GetMapping("/deleteCart")
    @ResponseBody
    public String deleteCart(Long productKey, Long userKey) {
        User user=userService.findUser(userKey);
        Product product=productService.findProduct(productKey);
        cartService.deleteCart(product, user);
        return "삭제 완료";
    }

    @GetMapping("/addCount")
    @ResponseBody
    public void addCount(Long productKey, Long userKey) {
        User user=userService.findUser(userKey);
        Product product=productService.findProduct(productKey);
        cartService.addCount(product, user);
        cartService.getCartTotal(user);
    }

    @GetMapping("/subCount")
    @ResponseBody
    public void subCount(Long productKey, Long userKey) {
        User user=userService.findUser(userKey);
        Product product=productService.findProduct(productKey);
        cartService.subCount(product, user);
    }

    @GetMapping("/getCartTotal")
    @ResponseBody
    public int getCartTotal(Long userKey){
        User user = userService.findUser(userKey);
        return cartService.getCartTotal(user);
    }

    @GetMapping("/goOrder")
    @ResponseBody
    public void goOrder(@RequestParam Long[] cartIdList, Long userKey, int delFee){
        User user = userService.findUser(userKey);
        cartService.goOrder(cartIdList, user, delFee);
    }
}
