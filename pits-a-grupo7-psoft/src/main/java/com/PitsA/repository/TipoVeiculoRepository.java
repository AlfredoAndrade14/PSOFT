package com.PitsA.repository;

import com.PitsA.model.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Long> {
    Optional<TipoVeiculo> findAllByTipo(String tipo);
}
