package com.ufcg.psoft.mercadofacil.dto;

import java.util.Date;

public class LoteDTO {

    private Long id;

    private Long produtoId;

    private int numeroDeItens;

    private Date dataDeValidade;

    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public Date getDataDeValidade() {
        return dataDeValidade;
    }
}
