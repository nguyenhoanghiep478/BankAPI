package com.example.bankapi.DTO.Movie;

import com.example.bankapi.Entity.Movie.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MovieResponse {
    private List<Movie> results;

}
