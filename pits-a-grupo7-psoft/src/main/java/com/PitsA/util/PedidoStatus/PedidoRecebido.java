package com.PitsA.util.PedidoStatus;

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
public class PedidoRecebido extends PedidoStatus{
    public PedidoRecebido(Pedido pedido) {
        super.pedido = pedido;
        super.status = "Pedido Recebido";
    }

    @Override
    public void mudaStatus(PedidoRepository pedidoRepository, PedidoStatusRepository pedidoStatusRepository) {
        PedidoStatus statusAntigo = this.pedido.getStatus();
        PedidoStatus novoStatus = new PedidoEmPreparo(this.pedido);
        pedidoStatusRepository.save(novoStatus);
        this.pedido.setStatus(novoStatus);
        pedidoRepository.save(this.pedido);
        pedidoStatusRepository.delete(statusAntigo);
    }
}
