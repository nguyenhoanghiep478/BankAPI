package com.example.bankapi.Repositories.Movie;

import com.example.bankapi.Entity.Movie.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}
