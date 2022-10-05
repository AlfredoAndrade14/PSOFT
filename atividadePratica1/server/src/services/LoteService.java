package services;

import model.Lote;
import model.Product;
import repository.LoteRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoteService {
    private LoteRepository repository;

    public LoteService() {
        this.repository = new LoteRepository();
    }

    public List<Lote> getLotes() {
        return this.repository.getLotes();
    }

    public Lote postLote(int qtd, Date dataDeValidade, Product product) {
        Lote lote = new Lote(qtd, dataDeValidade, product);
        this.repository.postLote(lote);
        return lote;
    }
}