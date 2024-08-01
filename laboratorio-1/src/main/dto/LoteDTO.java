package main.dto;

import main.models.Produto;

import java.util.Date;

public class LoteDTO {

	private String idProduto;

	private int quantidade;

	private Date dataFabricacao;

	public LoteDTO(String idProduto, int quantidade) {
		
		this.idProduto = idProduto;
		this.quantidade = quantidade;
	}

	public LoteDTO(Produto produto, int qtd, Date data_de_fabricacao) {
		this.idProduto = produto.getId();
		this.quantidade = qtd;
		this.dataFabricacao = data_de_fabricacao;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Date getDataFabricacao() {
		return dataFabricacao;
	}
}
