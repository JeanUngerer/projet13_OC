package com.yourcaryourway.chatappback.poc_back.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean sentByUser; // true: user, false: agent

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

}

