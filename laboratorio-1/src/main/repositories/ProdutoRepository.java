package main.repositories;

import java.util.*;

import main.models.Produto;

public class ProdutoRepository {
	
	private Map<String, Produto> produtos;
	
	public ProdutoRepository() {
		this.produtos = new HashMap<String, Produto>();
	}

	public Collection<Produto> getAll() {
		return this.produtos.values();
	}

	public Collection<Produto> getByName(String nome) {
		Collection<Produto> produtosComNome = new ArrayList<>();

		for (Produto p : this.produtos.values()) {
			if (p.getNome().toLowerCase().contains(nome.toLowerCase())) {
				produtosComNome.add(p);
			}
		}

		return produtosComNome;
	}

	public Produto getProd(String id) {
		return this.produtos.get(id);
	}
	
	public void delProd(String id) {
		this.produtos.remove(id);
	}
	
	public void editProd(Produto prod) {
		this.produtos.replace(prod.getId(), prod);
	}
	
	public String addProduto(Produto prod) {
		this.produtos.put(prod.getId(), prod);
		return(prod.getId());
	}

    public boolean containsProduto(String id) {
		return this.produtos.containsKey(id);
    }
}
