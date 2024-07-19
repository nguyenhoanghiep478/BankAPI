package com.example.bankapi.DTO.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;
}
