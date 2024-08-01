package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoFormDTO {
    private String endereco;
    private Set<PizzaPedidoFormDTO> pizzas;
}
