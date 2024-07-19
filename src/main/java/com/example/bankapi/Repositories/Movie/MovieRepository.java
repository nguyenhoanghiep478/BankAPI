package com.example.bankapi.Repositories.Movie;

import com.example.bankapi.Entity.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
