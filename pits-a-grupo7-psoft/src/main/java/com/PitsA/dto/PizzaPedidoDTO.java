package com.PitsA.dto;

import com.PitsA.model.PizzaPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PizzaPedidoDTO {

    private Long id;
    private SaborPizzaDTO saborPizzaUm;
    private SaborPizzaDTO saborPizzaDois;
    private Integer quantidade;

    public PizzaPedidoDTO(PizzaPedido pedido) {
        this.id = pedido.getId();
        this.saborPizzaUm = pedido.getSaborPizzaUm() == null? null : new SaborPizzaDTO(pedido.getSaborPizzaUm());
        this.saborPizzaDois = pedido.getSaborPizzaDois() == null? null : new SaborPizzaDTO(pedido.getSaborPizzaDois());
        this.quantidade = pedido.getQuantidade();
    }

    public PizzaPedido convert() {
        return new PizzaPedido(this.id,
                this.saborPizzaUm == null ? null : this.saborPizzaUm.convert(),
                this.saborPizzaDois  == null ? null : this.saborPizzaDois.convert(),
                this.quantidade);
    }
}
