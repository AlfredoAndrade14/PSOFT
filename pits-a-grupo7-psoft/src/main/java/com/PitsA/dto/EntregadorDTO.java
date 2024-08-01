package com.PitsA.dto;

import com.PitsA.model.Entregador;
import com.PitsA.util.ENUM.DisponibilidadeEntregador;
import com.PitsA.util.ENUM.EstadoAprovacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EntregadorDTO {
    private Long id;

    private String nome;

    private String placaVeiculo;

    private TipoVeiculoDTO tipoDoVeiculo;

    private String corDoVeiculo;

    @JsonIgnore
    private Integer codigoAcesso;

    @JsonIgnore
    private EstadoAprovacao estadoAprovacao;

    private DisponibilidadeEntregador disponibilidade;

    @JsonIgnore
    private Date tempoEspera;

    public EntregadorDTO(Entregador entregador) {
        this.id = entregador.getId();
        this.nome = entregador.getNome();
        this.placaVeiculo = entregador.getPlacaVeiculo();
        this.tipoDoVeiculo = new TipoVeiculoDTO(entregador.getTipoDoVeiculo());
        this.corDoVeiculo = entregador.getCorDoVeiculo();
        this.estadoAprovacao = entregador.getAceito();
        this.disponibilidade = entregador.getDisponibilidade();
        this.tempoEspera = entregador.getTempoEspera();
    }

    public Entregador convert() {
        return new Entregador(this.id,
                              this.nome,
                              this.placaVeiculo,
                              this.tipoDoVeiculo.convert(),
                              this.corDoVeiculo,
                              this.codigoAcesso,
                              this.estadoAprovacao,
                              this.disponibilidade,
                              this.tempoEspera);
    }
}
