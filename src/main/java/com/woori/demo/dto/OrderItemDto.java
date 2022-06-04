package com.woori.demo.dto;

import com.woori.demo.domain.OrderItem;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemDto {
    private Long id;
    private Long itemKey;
    private String itemName;
    private int itemPrice;
    private int itemCount;
    private int itemTotalPrice;
    private String itemPic;
    private int isItemCancel;

    public static OrderItemDto from(OrderItem orderItem){
        if(orderItem==null) return null;

        return OrderItemDto.builder()
                .id(orderItem.getId())
                .itemKey(orderItem.getItemKey())
                .itemName(orderItem.getItemName())
                .itemPrice(orderItem.getItemPrice())
                .itemCount(orderItem.getItemCount())
                .itemTotalPrice(orderItem.getItemTotalPrice())
                .itemPic(orderItem.getItemPic())
                .isItemCancel(orderItem.getIsItemCancel())
                .build();
    }
}
