package com.woori.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="orderKey")
    private Order order;

    private Long itemKey;
    private String itemName;
    private int itemPrice;
    private int itemCount;
    private int itemTotalPrice;
    private String itemPic;
    private int isItemCancel;

    @Builder
    public OrderItem(Long id, Order order, Long itemKey, String itemName, int itemPrice, int itemCount, int itemTotalPrice, String itemPic, int isItemCancel) {
        this.id = id;
        this.order = order;
        this.itemKey = itemKey;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemPic=itemPic;
        this.itemCount = itemCount;
        this.itemTotalPrice = itemTotalPrice;
        this.isItemCancel = isItemCancel;
    }
}
