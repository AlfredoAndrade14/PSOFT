package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EstabelecimentoFormDTO {

    private String nome;
    private String endereco;
    private Integer codigoAcesso;

}
