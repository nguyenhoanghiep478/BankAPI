package com.example.bankapi.Repositories.Movie;

import com.example.bankapi.Entity.Movie.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime,Long> {
}
