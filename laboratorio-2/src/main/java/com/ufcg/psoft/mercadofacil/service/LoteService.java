package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoSoldOutException;
import com.ufcg.psoft.mercadofacil.exception.fewerProdutoException;
import com.ufcg.psoft.mercadofacil.util.ErroQtdInvalida;

public interface LoteService {
	
	public List<LoteDTO> listarLotes();

	public LoteDTO criaLote(LoteDTO loteDTO) throws ProdutoNotFoundException, ErroQtdInvalida;

	public LoteDTO getLoteById(long id) throws LoteNotFoundException;

	public LoteDTO atualizaQtdLote(long id, int numItens) throws LoteNotFoundException, ErroQtdInvalida;

	public void removerLoteCadastrado(long id) throws LoteNotFoundException;

    public List<LoteDTO> findByProdutoId(Long id);

    public void checaQuantidadeDisponivel(Long id, int qtd) throws ProdutoSoldOutException, fewerProdutoException;

    public void compraProduto(Long id, int qtd) throws ProdutoSoldOutException, fewerProdutoException;
}
