package com.PitsA.dto;


import com.PitsA.model.SaborPizza;
import com.PitsA.util.Abstracts.Observer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaborPizzaDTO{

    private Long id;
    private String nome;
    private double valor;
    private TipoSaborDTO tipoSabor;
    private TamanhoPizzaDTO tamanhoPizza;
    private Boolean disponivel;

    public SaborPizzaDTO(SaborPizza saborPizza) {
        this.id = saborPizza.getId();
        this.nome = saborPizza.getNome();
        this.valor = saborPizza.getValor();
        this.tipoSabor = new TipoSaborDTO(saborPizza.getTipoSabor());
        this.tamanhoPizza = new TamanhoPizzaDTO(saborPizza.getTamanhoPizza());
        this.disponivel = saborPizza.getDisponivel();
    }

    public SaborPizza convert() {
        return new SaborPizza(this.id,
                              this.nome,
                              this.valor,
                              this.tipoSabor.convert(),
                              this.tamanhoPizza.convert(),
                              this.getDisponivel());
    }
}
