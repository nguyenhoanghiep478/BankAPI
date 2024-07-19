package com.example.bankapi.Entity.Movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seat;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auditorium_id", nullable = false)
    private Auditorium auditorium;
    private boolean isAvailable = false;
    private SeatType seatType ;
}
