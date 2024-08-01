package com.ufcg.psoft.mercadofacil.dto;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;

import java.util.Date;

public class CompraDTO {
    private Long id;

    private Cliente cliente;

    private Carrinho carrinho;

    private Date dataDeCompra;

    public Long getId() {return this.id;}

    public Long getCliente() {return cliente.getId();}

    public Carrinho getCarrinho() {return carrinho;}

    public Date getDataDeCompra() {
        return dataDeCompra;
    }
}
