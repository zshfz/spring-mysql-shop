package com.example.shop.entity;

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
    private String profileImageUrl;
    @Column(nullable = false, unique = true)
    private String displayName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
}
