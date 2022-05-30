package com.woori.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userKey")
    private Long id;

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
            joinColumns = {@JoinColumn(name = "userKey", referencedColumnName = "userKey")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder
    public User(Long id, String name, String email, String password, String address1, String address2, String zipcode, LocalDateTime createdAt, boolean activated, Set<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
        this.createdAt = createdAt;
        this.activated = activated;
        this.authorities = authorities;
    }
}
