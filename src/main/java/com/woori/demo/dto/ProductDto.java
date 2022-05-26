package com.woori.demo.dto;

import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private long product_key;
    private String product_name;
    private String product_num;
    private String product_category;
    private String product_pic;
    private Long fileId;
    private int product_price;
    private String product_desc;
    private int product_cnt;
    private boolean activated;

    public static ProductDto from(Product product) {
        if(product == null) return null;

        return ProductDto.builder()
                .product_key(product.getProduct_key())
                .product_name(product.getProduct_name())
                .product_num(product.getProduct_num())
                .product_category(product.getProduct_category())
                .product_pic(product.getProduct_pic())
                .fileId(product.getFileId())
                .product_price(product.getProduct_price())
                .product_desc(product.getProduct_desc())
                .product_cnt(product.getProduct_cnt())
                .build();
    }
}
