package com.yourcaryourway.chatappback.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userInfo;

    @CreatedDate
    @Column(name = "creation_date")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_update_date")
    LocalDateTime updatedAt;

    public RefreshToken() {

    }

    public RefreshToken(Long id, String token, Instant expiryDate, User userInfo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public RefreshToken(User userInfo, String token, Instant expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
