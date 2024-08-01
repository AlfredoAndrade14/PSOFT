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
public class PedidoPronto extends PedidoStatus {
    public PedidoPronto(Pedido pedido) {
        super.pedido = pedido;
        super.status = "Pedido Pronto";
    }

    @Override
    public void mudaStatus(PedidoRepository pedidoRepository, PedidoStatusRepository pedidoStatusRepository) {
        PedidoStatus statusAntigo = this.pedido.getStatus();
        PedidoStatus novoStatus =new PedidoEmRota(this.pedido);
        pedidoStatusRepository.save(novoStatus);
        this.pedido.setStatus(novoStatus);
        pedidoRepository.save(this.pedido);
        pedidoStatusRepository.delete(statusAntigo);
    }
}
