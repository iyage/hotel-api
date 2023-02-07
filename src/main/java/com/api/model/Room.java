package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
     private String type;
     private String description;
     private int maxPeople;
     private String roomNumber;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<BookDate> unavailableDate = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    public  Set<Photos>url = new HashSet<>();
}