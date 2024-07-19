package com.example.bankapi.Entity.Movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.example.bankapi.Entity.Movie.SeatType.NORMAL;
import static com.example.bankapi.Entity.Movie.SeatType.VIP;

@Entity
@Getter
@Setter
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int roomNumber;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="cinema_id")
    private Cinema cinema;
    private int seatsCount;
    @ManyToMany
    @JoinTable(
            name = "ShowTimeRoom",
            joinColumns = @JoinColumn(name = "auditorium_id"),
            inverseJoinColumns = @JoinColumn(name="showtime_id")
    )
    private Set<ShowTime> showtimes = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Seat> seats ;

    @PrePersist
    private void createSeats(){
        final int MAX_SEAT_PER_ROW = 12;
        final int MIN_SEAT_PER_ROW = 6;
        if(seats==null){
            this.seats = new HashSet<>();
            if (seatsCount < 6) {
                throw new IllegalArgumentException("Số ghế phải lớn hơn hoặc bằng 6.");
            }
            int totalRows = seatsCount/MAX_SEAT_PER_ROW;
            int remainSeats = seatsCount%MAX_SEAT_PER_ROW;
            int[] rows = new int[totalRows+1];
            Arrays.fill(rows, MAX_SEAT_PER_ROW);
            if(remainSeats<MIN_SEAT_PER_ROW){
                int fillSeats = MIN_SEAT_PER_ROW - remainSeats;
                rows[rows.length-1] = MIN_SEAT_PER_ROW;
                rows[rows.length-2] = MAX_SEAT_PER_ROW-fillSeats;
            }
            char rowChar = 'A';

            for (int row : rows) {
                for (int i = 1; i <= row; i++) {
                    Seat seat = new Seat();
                    seat.setSeat(rowChar + String.valueOf(i));
                    seat.setSeatType(determineSeatType(rowChar,i));
                    seat.setAuditorium(this);
                    seats.add(seat);
                }
                rowChar++;
            }
        }
    }
    private SeatType determineSeatType(char rowChar, int seatPosition) {
        if (rowChar >= 'E' && rowChar <= 'K' && (seatPosition <= 7) && (seatPosition >= 3)) {
            return SeatType.VIP;
        } else {
            return SeatType.NORMAL;
        }
    }
}
