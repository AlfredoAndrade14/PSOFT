package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Produto produto;

    private int qtd;

    public ItemCompra() {}

    public ItemCompra(Produto produto, int qtd) {
        this.produto = produto;
        this.qtd = qtd;
    }

    public Long getId() {return this.id;}

    public Produto getProduto() {return this.produto;}

    public int getQtd() {return this.qtd;}

    public void setQtd(int qtd) {this.qtd = qtd;}
}
