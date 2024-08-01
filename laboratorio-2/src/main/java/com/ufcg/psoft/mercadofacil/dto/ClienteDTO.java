package com.ufcg.psoft.mercadofacil.dto;

import com.ufcg.psoft.mercadofacil.model.Carrinho;

public class ClienteDTO {

	private Long id; 
	
	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;

	private Carrinho carrinho;

	public Long getId() {
		return id;
	}
	
	public Long getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}
	
	public Integer getIdade() {
		return idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public Carrinho getCarrinho() { return carrinho; }
}
