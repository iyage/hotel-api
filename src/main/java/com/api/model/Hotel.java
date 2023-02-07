package com.api.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "hotel_name", nullable = false)
    private String name;
    @Column(name = "hotel_type", nullable = false)
    private String type;
    @Column(name = "hotel_address", nullable = false)
    private String address;
    @Column(name = "city", nullable = false)
    private String city;
    private String  distance;
    private ArrayList<String> photos;
    private String Description;
    private int rating;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();
    private int cheapestPrice;
    private boolean featured = false;
}
