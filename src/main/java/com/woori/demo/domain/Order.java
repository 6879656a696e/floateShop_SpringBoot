package com.woori.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="userKey")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDateTime;
    private int delFee;
    private int isOrderCancel;
    private int totalPrice;

    @Builder
    public Order(Long id, User user, List<OrderItem> orderItems, LocalDateTime orderDateTime ,int delFee,  int isOrderCancel, int totalPrice) {
        this.id = id;
        this.user = user;
        this.orderItems = orderItems;
        this.orderDateTime = orderDateTime;
        this.delFee = delFee;
        this.isOrderCancel = isOrderCancel;
        this.totalPrice=totalPrice;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
    }

    public static Order createOrder(User user){
        Order order=new Order();
        order.user=user;
        return order;
    }

    public static Order createOrder(User user, List<OrderItem> orderItemList){
        Order order=new Order();
        for(OrderItem orderItem: orderItemList){
            order.addOrderItem(orderItem);
        }
        return order;
    }
}
