package com.sso.api.models;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uid = UUID.randomUUID();

    @Column(nullable = false, length = 100, unique = true)
    private String name;


    @Column(nullable = false, length = 256)
    private String secret;

    @Column(nullable = false, length = 100)
    private String redirectUri;

}
