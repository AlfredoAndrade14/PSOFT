import model.Lote;
import model.Product;
import services.LoteService;
import services.ProductService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Facade {
    ProductService productService;

    LoteService loteService;

    public Facade() {
        this.productService = new ProductService();
        this.loteService = new LoteService();
    }

    public List<Product> getProducts() {
        return this.productService.getProducts();
    }

    public List<Lote> getLotes() {
        return this.loteService.getLotes();
    }

    public Lote postLote(int qtd, Date dataDeValidade, Product product) {
        return this.loteService.postLote(qtd, dataDeValidade, product);
    }

    public Product postProduct(String nome, String fabricante, Double preco) {
        return this.productService.postProduct(nome, fabricante, preco);
    }
}
