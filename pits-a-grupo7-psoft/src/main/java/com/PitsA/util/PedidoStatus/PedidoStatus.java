package com.PitsA.util.PedidoStatus;

import com.PitsA.model.Pedido;
import com.PitsA.repository.PedidoRepository;
import com.PitsA.repository.PedidoStatusRepository;

import javax.persistence.*;

@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@Entity
public abstract class PedidoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String status;

    @ManyToOne
    protected Pedido pedido;

    public abstract void mudaStatus(PedidoRepository pedidoRepository, PedidoStatusRepository pedidoStatusRepository);

    public String toString() {
        return status;
    }
}
