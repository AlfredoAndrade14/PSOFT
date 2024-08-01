package main.services;

import java.util.Collection;

import com.google.gson.Gson;

import main.models.Produto;
import main.repositories.LoteRepository;
import main.repositories.ProdutoRepository;
import main.dto.ProdutoDTO;

public class ProdutoService {
	
	private ProdutoRepository prodRep;
	private Gson gson = new Gson();
	private LoteService loteService;
	
	public ProdutoService(ProdutoRepository prodRep) {
		this.prodRep = prodRep;
	}

	public void setLoteService(LoteService loteService) {
		this.loteService = loteService;
	}

	public Collection<Produto> listaProdutos() {
		return this.prodRep.getAll();
	}

	public Collection<Produto> listaProdutosPeloNome(String nome) {
		if(nome != null) {
			nome = nome.trim();

			if(!nome.isBlank()) {
				return this.prodRep.getByName(nome);
			} else throw new IllegalArgumentException("Parametro Não Permitido");
		} else throw new IllegalArgumentException("Parametro Não Permitido");
	}

	public String addProduto(String jsonData) {
		ProdutoDTO prodDTO= gson.fromJson(jsonData, ProdutoDTO.class);
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		
		this.prodRep.addProduto(produto);
		
		return produto.getId();
	}

	public String addProduto(String nome, String fabricante, double preco) {
		if(nome != null && fabricante != null){
			if(!nome.isBlank() && !fabricante.isBlank()){
				ProdutoDTO prodDTO = new ProdutoDTO(nome, fabricante, preco);
				Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());

				this.prodRep.addProduto(produto);

				return produto.getId();
			} else throw new IllegalArgumentException("Parametro Não Permitido");
		} else throw new IllegalArgumentException("Parametro Não Permitido");
	}

	public void delProduto(String id) {
		if(id != null) {
			if(!id.isEmpty()){
				if (this.prodRep.containsProduto(id)) {
					this.prodRep.delProd(id);
					this.loteService.delLoteFromDelProd(id);
				} else throw new IllegalArgumentException("Produto Não Encontrado");
			} else throw new IllegalArgumentException("Parametro Não Permitido");
		} else throw new IllegalArgumentException("Parametro Não Permitido");
	}

    public Produto listaProduto(String id) {
		if(this.prodRep.containsProduto(id)){
			return this.prodRep.getProd(id);
		} else throw new IllegalArgumentException("Produto Não Encontrado");
    }
}