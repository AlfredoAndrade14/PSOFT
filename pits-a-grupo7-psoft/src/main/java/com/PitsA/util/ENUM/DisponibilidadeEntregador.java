package com.PitsA.util.ENUM;

public enum DisponibilidadeEntregador {
    DESCANSO("Descanso"),
    ATIVO("Ativo");

    private String disponibilidade;

    DisponibilidadeEntregador(String estado) {
        this.disponibilidade = disponibilidade;
    }

    public String getEstado() {
        return this.disponibilidade;
    }
}
