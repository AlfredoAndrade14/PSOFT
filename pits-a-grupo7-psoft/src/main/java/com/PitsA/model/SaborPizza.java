package com.PitsA.model;

import com.PitsA.util.Abstracts.Observable;
import com.PitsA.util.Abstracts.Observer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@Component
@Entity
public class SaborPizza extends Observable {

    private String nome;
    private double valor;

    @ManyToOne
    @JoinColumn(name = "tipo_sabor_id")
    private TipoSabor tipoSabor;

    @ManyToOne
    @JoinColumn(name = "tamanho_pizza_id")
    private TamanhoPizza tamanhoPizza;

    private Boolean disponivel;

    public SaborPizza(Long id, String nome, Double valor, TipoSabor tipoSabor, TamanhoPizza tamanhoPizza, boolean disponivel) {
        super();
        id = id;
        this.nome = nome;
        this.valor = valor;
        this.tipoSabor = tipoSabor;
        this.tamanhoPizza = tamanhoPizza;
        this.disponivel = disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        if(!this.disponivel && disponivel){
            String mensagem = String.format("vem que o sabor %s agora está disponível!", this.nome);
            this.notifyObservers(mensagem);
        }
        this.disponivel = disponivel;
    }

    @Override
    public void notifyObservers(Object object) {
        for (Observer observer : super.getObservers()) {
            observer.update(object);
        }
    }
}
