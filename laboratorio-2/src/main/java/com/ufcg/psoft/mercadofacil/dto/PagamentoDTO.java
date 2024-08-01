package com.ufcg.psoft.mercadofacil.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;

import javax.persistence.OneToOne;

public class PagamentoDTO {
    private Long id;

    private Cliente cliente;

    private Compra compra;

    private double preco;

    public Compra getCompra() {
        return this.compra;
    }

    @JsonIgnore
    public Long getCliente() {
        return this.cliente.getId();
    }

    public double getPreco() {
        return this.preco;
    }
}
