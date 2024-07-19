package com.example.bankapi.Entity.Movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Column(nullable = false)
    private int roomAmounts;
    @Column(nullable = false)
    private int totalSeats;
    @OneToMany(mappedBy = "cinema",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Auditorium> auditoriums = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "CinemaMovie",
            joinColumns = @JoinColumn(name="cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> movies ;
    @PostLoad
    private void calculateTotalAuditoriums() {
        if(auditoriums != null){
            roomAmounts = auditoriums.size();
        }else{
            roomAmounts = 0;
        }
    }
    @PrePersist
    private void createAuditoriums(){
       if(auditoriums == null){
           this.auditoriums = new HashSet<>();
           int seatPerAuditorium = totalSeats/roomAmounts;
           for(int i =0;i<roomAmounts;i++){
               Auditorium aud = new Auditorium();
               aud.setCinema(this);
               aud.setRoomNumber(i+1);
               aud.setSeatsCount(seatPerAuditorium);
               this.auditoriums.add(aud);
           }
       }
    }
}
