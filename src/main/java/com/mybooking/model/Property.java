package com.mybooking.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "property")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "property")
    private List<Block> blocks;
}
