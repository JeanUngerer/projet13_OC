package com.yourcaryourway.chatappback.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String status;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}

