package com.example.bankapi.Service.Movie.impl;

import com.example.bankapi.DTO.Cinema.Cinema;
import com.example.bankapi.Repositories.Movie.CinemaRepository;
import com.example.bankapi.Service.Movie.ICinemaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService {
    private final CinemaRepository cinemaRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public boolean insertCinema(Cinema cinema) {
        com.example.bankapi.Entity.Movie.Cinema cinemaEntity = modelMapper.map(cinema, com.example.bankapi.Entity.Movie.Cinema.class);
        cinemaRepository.save(cinemaEntity);
        return true;
    }

    @Override
    public List<Cinema> getCinemas() {
        return List.of();
    }

    @Override
    public Cinema getCinemaByName(int id) {
        return null;
    }

    @Override
    public boolean updateCinema(Cinema cinema) {
        return false;
    }

    @Override
    public boolean insertAllCinema(List<Cinema> cinemas) {
        for (Cinema cinema : cinemas) {
            if(!insertCinema(cinema)){
                return false;
            };
        }
        return true;
    }
}
