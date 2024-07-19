package com.example.bankapi.Service.Movie.impl;

import com.example.bankapi.Entity.Movie.Cinema;
import com.example.bankapi.Entity.Movie.Movie;
import com.example.bankapi.Repositories.Movie.CinemaRepository;
import com.example.bankapi.Repositories.Movie.MovieRepository;
import com.example.bankapi.Repositories.Movie.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowTimeService {
    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    public void generateShowTime(){
        List<Movie> movies = movieRepository.findAll();
        List<Cinema> cinemas = cinemaRepository.findAll();
        LocalDateTime sevenAmToday = LocalDateTime.now().with(LocalTime.of(7,0));
        for(Movie movie : movies){

        }
    }
}
