package main.facades;

import main.repositories.LoteRepository;
import main.repositories.ProdutoRepository;
import main.services.LoteService;
import main.services.ProdutoService;
import main.models.Lote;
import main.models.Produto; 

import java.util.Collection;
import java.util.Date;

public class Facade {
	
	// Reposit�rios
	private ProdutoRepository produtoRep;
	private LoteRepository loteRep;
	
	// Servi�os
	private ProdutoService produtoService; 
	private LoteService loteService;
	
	
	public Facade() {
		this.produtoRep = new ProdutoRepository();
		this.loteRep = new LoteRepository();
		this.produtoService = new ProdutoService(this.produtoRep);
		this.loteService = new LoteService(loteRep, this.produtoService);
        this.produtoService.setLoteService(this.loteService);
	}
	
	public Collection<Produto> listaProdutos() {
		return this.produtoService.listaProdutos();
	}

	public Collection<Produto> listaProdutosPeloNome(String nome) {
		return this.produtoService.listaProdutosPeloNome(nome);
	}
	
	public Collection<Lote> listaLotes() {
		return this.loteService.listaLotes();
	}
		
	public String criaProduto(String data) {
		return this.produtoService.addProduto(data);
	}

	public String criaProduto(String nome, String fabricante, double preco) {
		return this.produtoService.addProduto(nome, fabricante, preco);
	}

	public void removeProduto(String id) {
		this.produtoService.delProduto(id);
	}

	public String criaLote(String data) {
		return this.loteService.addLote(data);
	}

	public String criaLote(Produto produto, int qtd, Date data_de_fabricacao) {
		return this.loteService.addLote(produto, qtd, data_de_fabricacao);
	}

	public void removeLote(String id) {
		this.loteService.delLote(id);
	}

	public Produto listaProdutoId(String id) {
		return this.produtoService.listaProduto(id);
	}

	public Collection<Produto> listaProdutosComLotePeloNome(String nome) {
		return this.loteService.listaProdutosComLotePeloNome(nome);
	}
}