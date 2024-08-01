package com.PitsA.dto;

import com.PitsA.model.TipoVeiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TipoVeiculoDTO {

    private Long id;
    private String tipo;

    public TipoVeiculoDTO(TipoVeiculo tipoVeiculo) {
        this.id = tipoVeiculo.getId();
        this.tipo = tipoVeiculo.getTipo();
    }

    public TipoVeiculo convert() {
        return new TipoVeiculo(this.id, this.tipo);
    }

    @Override
    public String toString() {
        return this.tipo;
    }
}
