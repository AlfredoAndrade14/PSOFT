package com.PitsA.dto;

import com.PitsA.model.TamanhoPizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TamanhoPizzaDTO  {

    private Long id;
    private String tamanho;

    public TamanhoPizzaDTO(TamanhoPizza tamanhoPizza) {
        this.id = tamanhoPizza.getId();
        this.tamanho = tamanhoPizza.getTamanho();
    }

    public TamanhoPizza convert() {
        return new TamanhoPizza(this.id, this.tamanho);
    }
}
