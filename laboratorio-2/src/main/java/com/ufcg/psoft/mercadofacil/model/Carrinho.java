package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ItemCompra> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    public Carrinho(List<ItemCompra> itens) {this.itens = itens;}

    public Long getId() {return this.id;}

    public List<ItemCompra> getItens() {return this.itens;}

    public void addItem(ItemCompra item){this.itens.add(item);}
}
