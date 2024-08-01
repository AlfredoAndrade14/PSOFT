package com.ufcg.psoft.mercadofacil.exception;

public class fewerProdutoException extends Throwable {
    private Long produtoId;
    private int qtd;

    public fewerProdutoException(Long produtoID, int qtd) {
        this.produtoId = produtoID;
        this.qtd = qtd;
    }

    public Long getProdutoId() {
        return this.produtoId;
    }

    public int getQtd() {
        return this.qtd;
    }
}
