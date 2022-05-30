package com.woori.demo.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cartItemKey")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cartKey")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="productKey")
    private Product product;

    private int count;

    @Builder
    public CartItem(Long id, Cart cart, Product product, int count) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.count = count;
    }

    public void addCount(int count){
        this.count+=count;
    }
}
