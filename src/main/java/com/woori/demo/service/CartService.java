package com.woori.demo.service;

import com.woori.demo.domain.Cart;
import com.woori.demo.domain.CartItem;
import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import com.woori.demo.exception.ResourceNotFoundException;
import com.woori.demo.repository.CartItemRepository;
import com.woori.demo.repository.CartRepository;
import com.woori.demo.repository.ProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

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

    public void addCart(Product netItem, User user, int count){
        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null){
            cart=Cart.createCart(user);
            cartRepository.save(cart);
        }

        Product product=productRepository.findProductByProductKey(netItem.getProductKey());
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
        updateCart.addTotal(cartRepository.totalNum());
        cartRepository.save(updateCart);
    }
}
