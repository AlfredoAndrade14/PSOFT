package com.PitsA.repository;

import com.PitsA.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    Optional<Estabelecimento> findById(Long id);

    @Query(value = "select * from estabelecimento e " +
            "join ENTREGADOR entrega on e.id = entrega.ESTABELECIMENTO_ID " +
            "where entrega.id = :idEntregador and entrega.ACEITO = 2", nativeQuery = true)
    Estabelecimento buscaEstabelecimento(@Param("idEntregador")Long idEntregador);
}
