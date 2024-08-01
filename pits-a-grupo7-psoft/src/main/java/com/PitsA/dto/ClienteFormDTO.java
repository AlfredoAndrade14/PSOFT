package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClienteFormDTO {

    private String nome;
    private String enderecoPrincipal;
    private Integer codigoAcesso;

}
