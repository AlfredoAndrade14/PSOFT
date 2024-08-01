package com.PitsA.util.TipoPagamento;

import org.springframework.stereotype.Component;

@Component
public class PagamentoDebito implements TipoPagamentoStrategy {
    @Override
    public Double realizaPagamento(Double preco) {
        return preco * 0.975;
    }
}
