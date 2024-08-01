package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadeSaborPizzaDTO {
    private Long idSaborPizza;
    private String sabor;
    private Boolean disponibilidade;

    public DisponibilidadeSaborPizzaDTO(SaborPizzaDTO saborPizzaDTO) {
        this.idSaborPizza = saborPizzaDTO.getId();
        this.sabor = saborPizzaDTO.getNome();
        this.disponibilidade = saborPizzaDTO.getDisponivel();
    }
}
