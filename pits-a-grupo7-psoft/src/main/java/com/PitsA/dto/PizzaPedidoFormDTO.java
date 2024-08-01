package com.PitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class PizzaPedidoFormDTO {
    private Long sabor1Id;
    private Long sabor2Id;
    private Integer quantidade;
}