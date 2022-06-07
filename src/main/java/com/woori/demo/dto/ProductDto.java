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
@ToString
public class ProductDto {
    private long productKey;
    private String productName;
    private String productNum;
    private String productCategory;
    private String productPic;
    private Long fileId;
    private int productPrice;
    private String productDesc;
    private int productCnt;
    private boolean activated;

    public static ProductDto from(Product product) {
        if(product == null) return null;

        return ProductDto.builder()
                .productKey(product.getProductKey())
                .productName(product.getProductName())
                .productNum(product.getProductNum())
                .productCategory(product.getProductCategory())
                .productPic(product.getProductPic())
                .fileId(product.getFileId())
                .productPrice(product.getProductPrice())
                .productDesc(product.getProductDesc())
                .productCnt(product.getProductCnt())
                .build();
    }
}
