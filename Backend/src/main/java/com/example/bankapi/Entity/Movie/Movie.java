package com.example.bankapi.Entity.Movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Movie {

    @Id
    private Long id;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("overview")
    @Column(length = 1000)
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("title")
    private String title;
    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private double voteAverage;
    private int duration;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ShowTime> showTimes = new HashSet<>();
    @ManyToMany(mappedBy = "movies")
    private Set<Cinema> cinemas = new HashSet<>();
}
