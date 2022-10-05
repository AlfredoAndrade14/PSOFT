package model;

public class Product {
    private String nome;
    private String fabricante;
    private double preco;

    public Product(String nome, String fabricante, double preco) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
