package com.PitsA.dto;

import com.PitsA.model.Cliente;
import com.PitsA.model.Pedido;
import com.PitsA.util.PedidoStatus.PedidoStatus;
import com.PitsA.util.ENUM.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoDTO {

    private Long id;

    private String endereco;

    private Set<PizzaPedidoDTO> pizzas;

    private double preco;

    private PedidoStatus status;

    @JsonIgnore
    private ClienteDTO clienteDTO;

    @JsonIgnore
    private EstabelecimentoDTO estabelecimentoDTO;

    private TipoPagamento tipoPagamento;

    private Date dataPedido;

    private EntregadorDTO entregadorDTO;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.endereco = pedido.getEndereco();
        this.pizzas = pedido.getPizzas().stream().map(PizzaPedidoDTO::new).collect(Collectors.toSet());
        this.preco = pedido.getPreco();
        this.status = pedido.getStatus();
        this.clienteDTO = new ClienteDTO((Cliente) pedido.getCliente());
        this.estabelecimentoDTO = new EstabelecimentoDTO(pedido.getEstabelecimento());
        this.tipoPagamento = pedido.getTipoPagamento();
        this.dataPedido = pedido.getDataPedido();
        this.entregadorDTO = pedido.getEntregador()==null? null:new EntregadorDTO(pedido.getEntregador());
    }

    public String getStatus() {
        return status.toString();
    }

    public Pedido convert() {
        return new Pedido(this.id,
        this.pizzas.stream().map(PizzaPedidoDTO::convert).collect(Collectors.toSet()),
        this.endereco,
        this.preco,
        this.status,
        this.clienteDTO.convert(),
        this.estabelecimentoDTO.convert(),
        this.tipoPagamento,
        this.dataPedido,
        this.entregadorDTO==null? null:this.entregadorDTO.convert());
    }
}
