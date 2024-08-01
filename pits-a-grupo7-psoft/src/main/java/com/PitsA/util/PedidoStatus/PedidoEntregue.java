package com.PitsA.util.PedidoStatus;

import com.PitsA.dto.PedidoDTO;
import com.PitsA.model.Pedido;
import com.PitsA.repository.PedidoRepository;
import com.PitsA.repository.PedidoStatusRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PedidoEntregue extends PedidoStatus {
    public PedidoEntregue(Pedido pedido) {
        super.pedido = pedido;
        super.status = "Pedido Entregue";
    }

    @Override
    public void mudaStatus(PedidoRepository pedidoRepository, PedidoStatusRepository pedidoStatusRepository) {

    }
}
