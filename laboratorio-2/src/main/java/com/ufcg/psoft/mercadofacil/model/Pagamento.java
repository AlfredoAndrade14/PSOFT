package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    @OneToOne
    private Compra compra;

    private double preco;

    public Pagamento() {}

    public Pagamento(Cliente cliente, Compra compra, double preco) {
        this.cliente = cliente;
        this.compra = compra;
        this.preco = preco;
    }

    public double getPreco() {
        return this.preco;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Compra getCompra() {
        return this.compra;
    }
}
