package main.repositories;

import java.util.*;

import main.models.Lote;
import main.models.Produto;

public class LoteRepository {
	
	private Map<String, Lote> lotes;
	
	public LoteRepository() {
		this.lotes = new HashMap<String, Lote>();
	}
	
	public Collection<Lote> getAll() {
		return this.lotes.values();
	}
	
	public Lote getLote(String id) {
		return this.lotes.get(id);
	}
	
	public void delLot(String id) {
		this.lotes.remove(id);
	}
	
	public void editLote(Lote lote) {
		this.lotes.replace(lote.getId(), lote);
	}
	
	public String addLote(Lote lote) {
		this.lotes.put(lote.getId(), lote);
		return lote.getId();
	}

	public void delLoteFromDelProd(String id) {
		for(Lote l: this.lotes.values()) {
			if(l.getProduto().getId() == id) {
				this.lotes.remove(l.getId());
			}
		}
	}

	public Collection<Produto> getProdByName(String nome) {
		Collection<Produto> produtos = new ArrayList<>();
		for(Lote l: this.lotes.values()){
			if(l.getProduto().getNome().trim().toLowerCase().contains(nome.toLowerCase())){
				produtos.add(l.getProduto());
			}
		}
		return produtos;
	}

    public boolean containsLote(String id) {
		return this.lotes.containsKey(id);
    }
}
