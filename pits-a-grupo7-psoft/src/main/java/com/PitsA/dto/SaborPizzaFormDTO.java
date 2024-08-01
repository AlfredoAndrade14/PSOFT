package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaborPizzaFormDTO {

    private String nome;
    private double valor;
    private Long tipoSaborId;
    private Long tamanhoPizzaId;

}
