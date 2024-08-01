package com.PitsA.repository;

import com.PitsA.model.SaborPizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaborPizzaRepository extends JpaRepository<SaborPizza, Long> {
    Optional<SaborPizza> findById(Long id);
}
