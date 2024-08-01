package com.PitsA.model;

import com.PitsA.util.ENUM.DisponibilidadeEntregador;
import com.PitsA.util.ENUM.EstadoAprovacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Entregador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String placaVeiculo;

    @ManyToOne
    @JoinColumn(name = "tipo_do_veiculo_id")
    private TipoVeiculo tipoDoVeiculo;

    private String corDoVeiculo;

    private Integer codigoAcesso;

    private EstadoAprovacao aceito;

    private DisponibilidadeEntregador disponibilidade;

    private Date tempoEspera;

    public void setDisponibilidade(DisponibilidadeEntregador disponibilidade) {
        if (disponibilidade.toString().equalsIgnoreCase("Ativo") && this.disponibilidade.toString().equalsIgnoreCase("Descanso")){
            this.tempoEspera = new Date();
        } else this.tempoEspera = null;
        this.disponibilidade = disponibilidade;
    }
}
