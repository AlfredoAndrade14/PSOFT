package com.PitsA.repository;

import com.PitsA.util.PedidoStatus.PedidoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoStatusRepository extends JpaRepository<PedidoStatus, Long> {
}
