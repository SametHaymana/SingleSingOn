package com.sso.api.models;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uid = UUID.randomUUID();


    @Column(nullable = false, length = 100, unique = true)
    private String username;

    // Email is unique
    @Column(nullable = false, length = 100, unique = true)
    private String email;


    // Password is hashed
    @Column(nullable = false, length = 256)
    private String password;


    /* User Info */

    @Column(nullable = true, length = 100)
    private String firstName;

    @Column(nullable = true, length = 100)
    private String lastName;

    @Column(nullable = true, length = 100)
    private String phone;


    /* Relations */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client; 


    /* Meta */

    @CreatedDate
    private Long createdAt;

    @UpdateTimestamp
    private Long updatedAt;

    @SoftDelete
    private Long deletedAt;


}
