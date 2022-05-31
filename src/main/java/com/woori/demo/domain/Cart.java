package com.woori.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.woori.demo.dto.CartDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @Column(name="cartKey")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="userKey")
    private User user;

    private int total=1;

    @JsonBackReference
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();


    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.user=user;
        return cart;
    }

    @Builder
    public Cart(Long id, User user, int total, List<CartItem> cartItems) {
        this.id = id;
        this.user = user;
        this.total = total;
        this.cartItems = cartItems;
    }

    public void setTotal(int total){
        this.total=total;
    }
}
