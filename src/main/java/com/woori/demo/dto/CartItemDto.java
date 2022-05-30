package com.woori.demo.dto;

import com.woori.demo.domain.Cart;
import com.woori.demo.domain.CartItem;
import com.woori.demo.domain.Product;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private Long id;
    private Cart cart;
    private Product product;
    private int count;

    public CartItem toEntity(){
        return CartItem.builder()
                .id(id)
                .cart(cart)
                .product(product)
                .count(count)
                .build();
    }

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cart = cartItem.getCart();
        this.product = cartItem.getProduct();
        this.count = cartItem.getCount();
    }
}
