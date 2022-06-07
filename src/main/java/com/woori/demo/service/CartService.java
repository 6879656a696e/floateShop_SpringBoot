package com.woori.demo.service;

import com.woori.demo.domain.*;
import com.woori.demo.repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
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

        Cart updateCart = cart;
        updateCart.setTotal(cartRepository.totalNum()==null ? 0 : cartRepository.totalNum());
        cartRepository.save(updateCart);
    }

    @Transactional
    public void subCount(Product newItem, User user){
        Cart cart = cartRepository.findByUserId(user.getId());
        Product product=productRepository.findProductByProductKey(newItem.getProductKey());
        CartItem cartItem = cartItemRepository.findByCartIdAndProductProductKey(cart.getId(), product.getProductKey());

        CartItem update = cartItem;
        update.subCount(1);
        cartItemRepository.save(update);

        Cart updateCart = cart;
        updateCart.setTotal(cartRepository.totalNum()==null ? 0 : cartRepository.totalNum());
        cartRepository.save(updateCart);

        if(cartItem.getCount()==0){
            cartItemRepository.delete(cartItem);
        }
    }

    public int getCartTotal(User user){
        Cart cart=cartRepository.findByUserId(user.getId());
        int totalNum=cartRepository.totalNum()==null ? 0 : cartRepository.totalNum();
        return totalNum;
    }

    @Transactional
    public void goOrder(Long[] cartIdList, User user, int delFee){
        Cart cart = cartRepository.findByUserId(user.getId());
        List<OrderItem> orderItemList = new ArrayList<>();
        Order order =Order.builder()
                .isOrderCancel(0)
                .user(user)
                .delFee(delFee)
                .orderDateTime(LocalDateTime.now())
                .totalPrice(delFee)
        .build();
        orderRepository.save(order);

        for(Long cartItemId: cartIdList){
            CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow();

            OrderItem orderItem= OrderItem.builder()
                    .order(order)
                    .itemKey(cartItem.getProduct().getProductKey())
                    .itemName(cartItem.getProduct().getProductName())
                    .itemCount(cartItem.getCount())
                    .itemPrice(cartItem.getProduct().getProductPrice())
                    .itemTotalPrice(cartItem.getCount()*cartItem.getProduct().getProductPrice())
                    .itemPic(cartItem.getProduct().getProductPic())
                    .isItemCancel(0)
                    .build();

            orderItemList.add(orderItem);
            cartItemRepository.deleteById(cartItemId);
            orderItemRepository.save(orderItem);

            Cart updateCart = cart;
            updateCart.setTotal(cartRepository.totalNum()==null ? 0 : cartRepository.totalNum());
            cartRepository.save(updateCart);
        }

        int tot=orderRepository.totalItemPrice();
        Order update = order;
        update =Order.builder()
                .id(order.getId())
                .isOrderCancel(0)
                .user(user)
                .delFee(delFee)
                .orderDateTime(LocalDateTime.now())
                .totalPrice(delFee+tot)
                .build();
        orderRepository.save(update);
    }
}
