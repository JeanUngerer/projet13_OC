package com.yourcaryourway.chatappback.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String country;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

}

