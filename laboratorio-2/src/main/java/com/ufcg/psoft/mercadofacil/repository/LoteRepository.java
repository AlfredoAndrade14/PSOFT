package com.ufcg.psoft.mercadofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Lote;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long>{
    List<Lote> findByProdutoIdOrderByDataDeValidadeAsc(Long produto_id);
}