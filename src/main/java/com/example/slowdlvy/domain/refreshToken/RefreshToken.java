package com.example.slowdlvy.domain.refreshToken;

import javax.persistence.*;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KEY_USERNAME", nullable = false)
    private String username;

    @Column(name = "REFRESH_TOKEN", updatable = false)
    private String refreshToken;
}
