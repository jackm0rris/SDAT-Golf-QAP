package com.keyin.Repositories;

import com.keyin.Entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByLocationContaining(String location);
    List<Tournament> findByStartDate(LocalDate startDate);
}