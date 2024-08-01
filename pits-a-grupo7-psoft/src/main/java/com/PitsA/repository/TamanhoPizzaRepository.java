package com.PitsA.repository;

import com.PitsA.model.TamanhoPizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TamanhoPizzaRepository extends JpaRepository<TamanhoPizza, Long> {

    Optional<TamanhoPizza> findById(Long id);
}
