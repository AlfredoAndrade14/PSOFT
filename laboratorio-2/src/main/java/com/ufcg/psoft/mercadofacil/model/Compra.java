package com.ufcg.psoft.mercadofacil.model;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    @OneToOne
    private Carrinho carrinho;

    @CreatedDate
    private Date dataDeCompra;

    public Compra() {}

    public Compra(Cliente cliente, Carrinho carrinho) {
        this.cliente = cliente;
        this.carrinho = carrinho;
        this.dataDeCompra = new Date();
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Carrinho getCarrinho() {
        return this.carrinho;
    }

    public Long getId() {
        return this.id;
    }
}
