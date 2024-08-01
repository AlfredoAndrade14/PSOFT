package com.PitsA.util.ENUM;

public enum EstadoAprovacao {

    RECUSADO("Recusado"),
    EM_APROVACAO("Em aprovação"),
    ACEITO("Aceito");

    private String estado;

    EstadoAprovacao(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}
