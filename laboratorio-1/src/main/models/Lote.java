package main.models;
import java.util.Date;
import java.util.UUID;

public class Lote {
	
	private String id;
	
	private Produto produto;
	
	private int quantidade; 
	
	private Date dataValidade;

	private Date dataFabricacao;
	
	public Lote(Produto produto, int quantidade) {
		
		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public Lote(Produto produto, int quantidade, Date data_de_fabricacao) {

		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataFabricacao = data_de_fabricacao;
	}

	public String getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public Date getDataFabricacao() {return dataFabricacao;}

	public String toString() {
		return "Lote ID: " + getId() + " - Produto: " + getProduto().getNome() + " - " + getQuantidade() + " itens";
	}
}
