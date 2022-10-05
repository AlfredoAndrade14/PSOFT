package model;

import java.util.Date;
public class Lote {
    private int qtd;

    private Date dataDeValidade;

    private Product product;

    public Lote(int qtd, Date data, Product product) {
        this.qtd = qtd;

        this.dataDeValidade = data;

        this.product = product;
    }

    public Date getData() {
        return dataDeValidade;
    }

    public int getQtd() {
        return qtd;
    }

    public Product getProduto() {
        return product;
    }

    @Override
    public String toString(){
        return this.product + " " + this.qtd + " " + this.dataDeValidade;
    }
}
