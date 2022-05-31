package com.woori.demo.service;

import com.woori.demo.domain.Cart;
import com.woori.demo.domain.CartItem;
import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import com.woori.demo.repository.CartItemRepository;
import com.woori.demo.repository.CartRepository;
import com.woori.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void addCart(Product newItem, User user, int count){
        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null){
            cart=Cart.createCart(user);
            cartRepository.save(cart);
        }

        Product product=productRepository.findProductByProductKey(newItem.getProductKey());
        CartItem cartItem = cartItemRepository.findByCartIdAndProductProductKey(cart.getId(), product.getProductKey());

        if(cartItem==null){
            cartItem= CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .count(1)
                    .build();
            cartItemRepository.save(cartItem);
        } else {
            CartItem update = cartItem;
            update.addCount(1);
            cartItemRepository.save(update);
        }

        Cart updateCart = cart;
        updateCart.setTotal(cartRepository.totalNum());
        cartRepository.save(updateCart);
    }

    @Transactional
    public List<CartItem> getCartList(Long userKey){
        Cart cart = cartRepository.findByUserId(userKey);
        return cartItemRepository.findCartItemByCartId(cart.getId());
    }

    @Transactional
    public void deleteCart(Product newItem, User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        CartItem cartItem = cartItemRepository.findByCartIdAndProductProductKey(cart.getId(), newItem.getProductKey());
        cartItemRepository.delete(cartItem);

        Cart updateCart = cart;
        updateCart.setTotal(cartRepository.totalNum()==null ? 0 : cartRepository.totalNum());
        cartRepository.save(updateCart);
    }

    @Transactional
    public void addCount(Product newItem, User user){
        Cart cart = cartRepository.findByUserId(user.getId());
        Product product=productRepository.findProductByProductKey(newItem.getProductKey());
        CartItem cartItem = cartItemRepository.findByCartIdAndProductProductKey(cart.getId(), product.getProductKey());

        CartItem update = cartItem;
        update.addCount(1);
        cartItemRepository.save(update);
    }

    @Transactional
    public void subCount(Product newItem, User user){
        Cart cart = cartRepository.findByUserId(user.getId());
        Product product=productRepository.findProductByProductKey(newItem.getProductKey());
        CartItem cartItem = cartItemRepository.findByCartIdAndProductProductKey(cart.getId(), product.getProductKey());

        CartItem update = cartItem;
        update.subCount(1);
        cartItemRepository.save(update);

        if(cartItem.getCount()==0){
            cartItemRepository.delete(cartItem);
        }
    }

}
