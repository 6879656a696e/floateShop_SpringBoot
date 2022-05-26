package com.woori.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long product_key;

    @Column(nullable=false)
    private String product_name;

    @Column(nullable = false)
    private String product_num;

    @Column(nullable=false)
    private String product_category;

    @Column
    private String product_pic;

    @Column
    private Long fileId;

    @Column(nullable = false)
    private int product_price;

    @Column(columnDefinition = "LONGTEXT")
    private String product_desc;

    @Column(nullable=false)
    private int product_cnt;

    @Column
    private boolean activated;

    @Builder
    public Product(Long product_key, String product_name, String product_num, String product_category, String product_pic, Long fileId, int product_price, String product_desc, int product_cnt, boolean activated) {
        this.product_key = product_key;
        this.product_name = product_name;
        this.product_num = product_num;
        this.product_category = product_category;
        this.product_pic = product_pic;
        this.fileId = fileId;
        this.product_price = product_price;
        this.product_desc = product_desc;
        this.product_cnt = product_cnt;
        this.activated = activated;
    }
}
