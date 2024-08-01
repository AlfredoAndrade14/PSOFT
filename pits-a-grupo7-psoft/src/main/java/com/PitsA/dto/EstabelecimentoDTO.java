package com.PitsA.dto;

import com.PitsA.model.Estabelecimento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class EstabelecimentoDTO {

    private Long id;

    private String nome;

    private String endereco;

    @JsonIgnore
    private Integer codigoAcesso;

    @JsonIgnore
    private Set<EntregadorDTO> entregadores;

    @JsonIgnore
    private Set<SaborPizzaDTO> saboresPizzas;

    public EstabelecimentoDTO(Estabelecimento estabelecimento) {
        this.id = estabelecimento.getId();
        this.nome = estabelecimento.getNome();
        this.endereco = estabelecimento.getEndereco();
        this.entregadores = estabelecimento.getEntregadores().stream()
                .map(entregador -> new EntregadorDTO(entregador)).collect(Collectors.toSet());
        this.saboresPizzas = estabelecimento.getSaborPizzaSet()
                .stream().map(saborPizza -> new SaborPizzaDTO(saborPizza))
                .collect(Collectors.toSet());
    }

    public Estabelecimento convert() {
        return new Estabelecimento(
                this.id,
                this.nome,
                this.endereco,
                this.codigoAcesso,
                this.entregadores.stream().map(EntregadorDTO::convert).collect(Collectors.toSet()),
                this.saboresPizzas.stream().map(SaborPizzaDTO::convert).collect(Collectors.toSet()));
    }
}
