package com.sso.api.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36, unique = true)
    private UUID uid = UUID.randomUUID();

    @Column(nullable = false, length = 100, unique = true)
    private String name;


    // Secret is random
    @Column(nullable = false, length = 256)
    private String secret;

    // Default redirect uri of the client
    @Column(nullable = false, length = 100)
    private String redirectUri;


    /* Relations */

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();



    /* Meta */
    
    @CreatedDate
    private Long createdAt;

    @UpdateTimestamp
    private Long updatedAt;

    @SoftDelete
    private Long deletedAt;

}
