package com.PitsA.util.ENUM;

public enum TipoPagamento {

    EM_AGUARDO("EM_AGUARDO"),
    CARTAO_CREDITO("CARTAO_CREDITO"),
    CARTAO_DEBITO("CARTAO_DEBITO"),
    PIX("PIX");

    private String estado;

    TipoPagamento(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}
