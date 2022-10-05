package repository;

import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private Map<Integer, Product> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    public void postProduct(Product product) {
        int id = this.products.size() + 1;
        this.products.put(id, product);
    }

    public List<Product> getProducts() {
        List<Product> listagemProduct = new ArrayList<>(this.products.values());
        return listagemProduct;
    }
}