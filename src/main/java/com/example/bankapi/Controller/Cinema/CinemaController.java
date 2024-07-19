package com.example.bankapi.Controller.Cinema;

import com.example.bankapi.DTO.Cinema.Cinema;
import com.example.bankapi.Service.Movie.impl.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class CinemaController {
    private final CinemaService cinemaService;
//    @GetMapping("/get-cinemas")
//    public List<Cinema> getCinemas() throws IOException {
//        return cinemaService.getAllCinemas();
//    }
    @PostMapping("/add-cinemas")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean addCinema(@RequestBody List<Cinema> cinemas) {
        return this.cinemaService.insertAllCinema(cinemas);
    }
}
