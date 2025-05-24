package com.basic_shop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String displayName;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
}
