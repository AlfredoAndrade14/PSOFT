package com.PitsA.repository;

import com.PitsA.model.TipoSabor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoSaborRepository extends JpaRepository<TipoSabor, Long> {
    Optional<TipoSabor> findById(Long id);

}
