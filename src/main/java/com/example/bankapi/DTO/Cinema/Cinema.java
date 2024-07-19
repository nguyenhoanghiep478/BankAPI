package com.example.bankapi.DTO.Cinema;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Cinema {
    private String name;
    private String address;
    @Size(min = 1)
    private int roomAmounts;
    @Size(min = 6)
    private int totalSeats;
    Set<Auditorium> auditoriums;
}
