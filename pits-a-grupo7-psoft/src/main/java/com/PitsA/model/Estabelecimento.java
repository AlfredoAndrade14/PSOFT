package com.PitsA.model;

import com.PitsA.util.Abstracts.Observer;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@Entity
public class Estabelecimento extends Observer {

    private String nome;

    private String endereco;

    private Integer codigoAcesso;

    @OneToMany
    @JoinColumn(name = "estabelecimento_id")
    private Set<Entregador> entregadores;

    @OneToMany
    private Set<SaborPizza> saborPizzaSet;

    public Estabelecimento(Long id, String nome, String endereco, Integer codigoAcesso, Set<Entregador> entregadores, Set<SaborPizza> sabores) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.codigoAcesso = codigoAcesso;
        this.entregadores = entregadores;
        this.saborPizzaSet = sabores;
    }

    public void addEntregador(Entregador entregador){
        this.entregadores.add(entregador);
    }

    public void addSaborPizza(SaborPizza saborPizza){
        this.saborPizzaSet.add(saborPizza);
    }

    @Override
    public void update(Object object) {
        System.out.println(String.format("%s, o pedido de id %s foi entregue, confirmado pelo cliente!", this.nome, object));
    }
}
