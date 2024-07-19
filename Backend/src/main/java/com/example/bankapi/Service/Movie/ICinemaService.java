package com.example.bankapi.Service.Movie;

import com.example.bankapi.DTO.Cinema.Cinema;

import java.util.List;

public interface ICinemaService {
    boolean insertCinema(Cinema cinema);
    List<Cinema> getCinemas();
    Cinema getCinemaByName(int id);
    boolean updateCinema(Cinema cinema);
    boolean insertAllCinema(List<Cinema> cinemas);
}
