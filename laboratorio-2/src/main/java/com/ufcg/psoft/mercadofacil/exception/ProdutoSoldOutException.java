package com.ufcg.psoft.mercadofacil.exception;

public class ProdutoSoldOutException extends Throwable {
    private Long produtoId;

    public ProdutoSoldOutException(Long produtoID) {
        this.produtoId = produtoID;
    }

    public Long getProdutoId() {
        return this.produtoId;
    }
}
