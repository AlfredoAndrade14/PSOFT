package com.PitsA.model;

import com.PitsA.util.Abstracts.Observable;
import com.PitsA.util.ENUM.TipoPagamento;
import com.PitsA.util.PedidoStatus.PedidoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Component
public class Pedido extends Observable {

    @OneToMany(cascade=CascadeType.REMOVE)
    private Set<PizzaPedido> pizzas;

    private String endereco;

    private Double preco;

    @ManyToOne(cascade=CascadeType.REMOVE)
    private PedidoStatus status;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Estabelecimento estabelecimento;

    private TipoPagamento tipoPagamento;

    @CreatedDate
    private Date dataPedido;

    @ManyToOne
    private Entregador entregador;

    public Pedido(Long id,
                  Set<PizzaPedido> pizzas,
                  String endereco,
                  Double preco,
                  PedidoStatus pedidoStatus,
                  Cliente cliente,
                  Estabelecimento estabelecimento,
                  TipoPagamento tipoPagamento,
                  Date dataPedido,
                  Entregador entregador) {
        super.id = id;
        this.pizzas = pizzas;
        this.endereco = endereco;
        this.preco = preco;
        this.status = pedidoStatus;
        this.cliente = cliente;
        this.estabelecimento = estabelecimento;
        this.tipoPagamento = tipoPagamento;
        this. dataPedido = dataPedido;
        this.entregador = entregador;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
        if (status.toString().equals("Pedido Em Rota")) this.notificaCliente();
        else if (status.toString().equals("Pedido Entregue")) this.notificaEstabelecimento();
    }


    private void notificaEstabelecimento() {
        estabelecimento.update(this.id);
    }


    @Override
    public void notifyObservers(Object object) {

    }

    private void notificaCliente() {

        String dados_entregador = String.format("nome: %s\nplaca do veículo: %s\ncor do veículo: %s\ntipo do veículo: %s",
                this.entregador.getNome(),
                this.entregador.getPlacaVeiculo(),
                this.entregador.getCorDoVeiculo(),
                this.entregador.getTipoDoVeiculo());

        this.cliente.update("seu pedido saiu para a entrega, as informações do entregador são: " + dados_entregador);
    }
}
