package com.example.bankapi.Service.Movie.impl;

import com.example.bankapi.DTO.Movie.MovieResponse;
import com.example.bankapi.Entity.Movie.Movie;
import com.example.bankapi.Repositories.Movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    @Value("${THE_MOVIE_DB_KEY}")
    private String apiKey;
    private final RestTemplate restTemplate;
    public List<Movie> getAllMovies() {
        LocalDate today = LocalDate.now();
        LocalDate weekBefore = today.minusDays(7);
        LocalDate weekAfter = today.plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String url = String.format(
                "https://api.themoviedb.org/3/discover/movie?api_key=%s&release_date.gte=%s&release_date.lte=%s",
                apiKey,
                weekBefore.format(formatter),
                weekAfter.format(formatter)
        );
        MovieResponse movieResponse =restTemplate.getForObject(url, MovieResponse.class);
        int duration;
        Random random = new Random();
        if(movieResponse != null) {

            List<Movie> movies = movieResponse.getResults();
            for(Movie movie : movies) {
                duration=random.nextInt((150-90) +1)+90;
                movie.setDuration(duration);
                movie.setPosterPath("https://image.tmdb.org/t/p/w500"+movie.getPosterPath());
            }
            this.movieRepository.saveAll(movies);
        }
        return movieRepository.findAll();
    }
}
