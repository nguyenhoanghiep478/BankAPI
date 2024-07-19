package com.example.bankapi.Controller.MovieController;

import com.example.bankapi.Service.Movie.impl.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping("/all-movies")
    public void GetAllMovie(){
        movieService.getAllMovies();
    }
}
