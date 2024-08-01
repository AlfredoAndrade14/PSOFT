package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;

	@OneToOne
	private Carrinho carrinho;

	public Cliente() {}

	public Cliente(Long cpf, String nome, Integer idade, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
	}

	public Cliente(Long cpf, String nome, Integer idade, String endereco, Carrinho carrinho) {
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.carrinho = carrinho;
	}

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

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public boolean temCarrinho() {return this.carrinho != null;}

	public Carrinho getCarrinho(){return carrinho;}

	public void setCarrinho(Carrinho carrinho){this.carrinho = carrinho;}
}
