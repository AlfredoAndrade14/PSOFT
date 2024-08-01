package com.PitsA.repository;

import com.PitsA.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}
