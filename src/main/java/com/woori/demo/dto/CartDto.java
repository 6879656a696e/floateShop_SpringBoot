package com.woori.demo.dto;

import com.woori.demo.domain.Cart;
import com.woori.demo.domain.CartItem;
import com.woori.demo.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
public class CartDto {
    private Long id;
    private User user;
    private int total = 0;
    private List<CartItem> cartItems = new ArrayList<>();
}


