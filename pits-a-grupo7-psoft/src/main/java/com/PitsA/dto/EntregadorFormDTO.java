package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EntregadorFormDTO {

    private String nome;
    private String placaVeiculo;
    private Long tipoVeiculoId;
    private String corDoVeiculo;
    private Integer codigoAcesso;

}
