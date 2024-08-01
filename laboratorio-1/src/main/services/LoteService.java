package main.services;

import java.util.Collection;
import java.util.Date;

import com.google.gson.Gson;

import main.dto.LoteDTO;
import main.models.Lote;
import main.models.Produto;
import main.repositories.LoteRepository;
import main.repositories.ProdutoRepository;

public class LoteService {

	private LoteRepository loteRep;
	private ProdutoService produtoService;
	private Gson gson = new Gson();
	
	public LoteService(LoteRepository loteRep, ProdutoService prodService) {
		this.loteRep = loteRep;
		this.produtoService = prodService;
	}
	
	public Collection<Lote> listaLotes() {
		return this.loteRep.getAll();
	}
	
	public String addLote(String jsonData) {
		LoteDTO loteDTO = gson.fromJson(jsonData, LoteDTO.class);
		Produto prod = this.produtoService.listaProduto(loteDTO.getIdProduto());
		
		Lote lote = new Lote(prod, loteDTO.getQuantidade());
		this.loteRep.addLote(lote);

		return lote.getId();
	}

	public String addLote(Produto produto, int qtd, Date data_de_fabricacao) {
		if(produto != null && data_de_fabricacao != null){
			LoteDTO loteDTO = new LoteDTO(produto, qtd, data_de_fabricacao);
			Produto prod = this.produtoService.listaProduto(loteDTO.getIdProduto());

			Lote lote = new Lote(prod, loteDTO.getQuantidade(), loteDTO.getDataFabricacao());
			this.loteRep.addLote(lote);

			return lote.getId();
		} else throw new IllegalArgumentException("Parametro Não Permitido");
	}

	public void delLote(String id) {
		if(id != null){
			if(!id.isEmpty()){
				if (this.loteRep.containsLote(id)) {
					this.loteRep.delLot(id);
				} else throw new IllegalArgumentException("Lote Não Encontrado");
			} else throw new IllegalArgumentException("Parametro Não Permitido");
		} else throw new IllegalArgumentException("Parametro Não Permitido");
	}

	public void delLoteFromDelProd(String id) {
		this.loteRep.delLoteFromDelProd(id);
	}

	public Collection<Produto> listaProdutosComLotePeloNome(String nome) {
		if(nome != null) {
			nome = nome.trim();
			if(!nome.isBlank()){
				return this.loteRep.getProdByName(nome);
			} else throw new IllegalArgumentException("Parametro Não Permitido");
		} else throw new IllegalArgumentException("Parametro Não Permitido");
	}
}