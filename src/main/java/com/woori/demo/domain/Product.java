package com.woori.demo.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@ToString
@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productKey")
    private Long productKey;

    @Column(name="productName", nullable=false)
    private String productName;

    @Column(name="productNum", nullable = false)
    private String productNum;

    @Column(name="productCategory", nullable=false)
    private String productCategory;

    @Column(name="productPic")
    private String productPic;

    @Column
    private Long fileId;

    @Column(name="productPrice", nullable = false)
    private int productPrice;

    @Column(name="productDesc", columnDefinition = "LONGTEXT")
    private String productDesc;

    @Column(name="productCnt", nullable=false)
    private int productCnt;

    @Column
    private boolean activated;

    @Builder
    public Product(Long productKey, String productName, String productNum, String productCategory, String productPic, Long fileId, int productPrice, String productDesc, int productCnt, boolean activated) {
        this.productKey = productKey;
        this.productName = productName;
        this.productNum = productNum;
        this.productCategory = productCategory;
        this.productPic = productPic;
        this.fileId = fileId;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.productCnt = productCnt;
        this.activated = activated;
    }
}
