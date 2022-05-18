package com.woori.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long user_key;

    @Column(length=50, nullable=false)
    private String name;

    @Column(length=50, nullable=false)
    private String email;

    @Column(length=60, nullable=false)
    private String password;

    @Column(length=100, nullable = false)
    private String address1;

    @Column(length=100)
    private String address2;

    @Column(length=5)
    private String zipcode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_key", referencedColumnName = "user_key")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
