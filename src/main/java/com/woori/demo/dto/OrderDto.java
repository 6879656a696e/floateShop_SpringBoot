package com.woori.demo.dto;

import com.woori.demo.domain.Order;
import com.woori.demo.domain.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime orderDateTime;
    private int isOrderCancel;
    private int delFee;
    private int totalPrice;

    public static OrderDto from(Order order){
        if(order==null) return null;

        return OrderDto.builder()
                .id(order.getId())
                .orderItems(order.getOrderItems())
                .orderDateTime(order.getOrderDateTime())
                .delFee(order.getDelFee())
                .isOrderCancel(order.getIsOrderCancel())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
