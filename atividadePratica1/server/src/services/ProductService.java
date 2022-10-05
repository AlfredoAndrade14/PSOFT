package services;

import model.Product;
import repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class ProductService {
    private ProductRepository repository;

    public ProductService() {
        this.repository = new ProductRepository();
    }

    public List<Product> getProducts() {
        return this.repository.getProducts();
    }

    public Product postProduct(String nome, String fabricante, double preco) {
        Product produto = new Product(nome, fabricante, preco);
        this.repository.postProduct(produto);
        return produto;
    }
}