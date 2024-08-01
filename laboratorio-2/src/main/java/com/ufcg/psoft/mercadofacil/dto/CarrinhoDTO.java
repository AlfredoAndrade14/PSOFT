package com.ufcg.psoft.mercadofacil.dto;


import com.ufcg.psoft.mercadofacil.model.ItemCompra;

import java.util.List;

public class CarrinhoDTO {
    private Long id;

    private List<ItemCompra> itens;

    public Long getId() {return this.id;}

    public List<ItemCompra> getItens() {return this.itens;}

    public void setItens(List<ItemCompra> itens){this.itens = itens;}
}
