package repository;

import model.Lote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoteRepository {
    private Map<Integer,Lote> lotes;

    public LoteRepository() {
        this.lotes = new HashMap();
    }

    public List<Lote> getLotes() {
        List<Lote> listagemLotes = new ArrayList<>(this.lotes.values());
        return listagemLotes;
    }

    public void  postLote(Lote lote) {
        int id = this.lotes.size() + 1;
        this.lotes.put(id,lote);
    }
}