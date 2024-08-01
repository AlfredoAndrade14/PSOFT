package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoSoldOutException;
import com.ufcg.psoft.mercadofacil.exception.fewerProdutoException;
import com.ufcg.psoft.mercadofacil.util.ErroQtdInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

@Service
public class LoteServiceImpl implements LoteService {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	public List<LoteDTO> listarLotes() {
		return loteRepository.findAll()
				.stream()
				.map(lote -> modelMapper.map(lote, LoteDTO.class))
				.collect(Collectors.toList());

	}

	private void salvarLote(Lote lote) {
		loteRepository.save(lote);		
	}

	public LoteDTO criaLote(LoteDTO loteDTO) throws ProdutoNotFoundException, ErroQtdInvalida {
		Produto produto = produtoService.getProduto(loteDTO.getProdutoId());
		if(loteDTO.getNumeroDeItens() <= 0) {
			throw new ErroQtdInvalida();
		}
		Lote lote = new Lote(produto, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade());
		salvarLote(lote);
	
		return modelMapper.map(lote, LoteDTO.class);
	}

	@Override
	public LoteDTO getLoteById(long id) throws LoteNotFoundException {
		Lote lote = getLote(id);
		return modelMapper.map(lote, LoteDTO.class);
	}

	@Override
	public LoteDTO atualizaQtdLote(long id, int numItens) throws LoteNotFoundException, ErroQtdInvalida {
		Lote lote = getLote(id);
		if(numItens <= 0) {
			throw new ErroQtdInvalida();
		}
		lote.setNumeroDeItens(numItens);
		salvarLote(lote);

		return modelMapper.map(lote, LoteDTO.class);
	}

	@Override
	public void removerLoteCadastrado(long id) throws LoteNotFoundException {
		Lote lote = getLote(id);
		loteRepository.delete(lote);
	}

	@Override
	public List<LoteDTO> findByProdutoId(Long id) {
		return loteRepository.findByProdutoIdOrderByDataDeValidadeAsc(id)
				.stream()
				.map(lote -> modelMapper.map(lote, LoteDTO.class))
				.collect(Collectors.toList());
	}

	private Lote getLote(Long id) throws LoteNotFoundException {
		return loteRepository.findById(id)
				.orElseThrow(LoteNotFoundException::new);
	}

	@Override
	public void checaQuantidadeDisponivel(Long produtoID,int qtd) throws ProdutoSoldOutException, fewerProdutoException {
		List<Lote> lotes = loteRepository.findByProdutoIdOrderByDataDeValidadeAsc(produtoID);
		if(lotes.size() == 0) {
			throw new ProdutoSoldOutException(produtoID);
		} else {
			int qtdProds = 0;
			for(Lote lote: lotes) {
				qtdProds += lote.getNumeroDeItens();
			}
			if(qtdProds < qtd) {throw new fewerProdutoException(produtoID,qtd);}
		}
	}

	@Override
	public void compraProduto(Long id, int qtd) throws ProdutoSoldOutException, fewerProdutoException {
		checaQuantidadeDisponivel(id,qtd);
		List<Lote> lotes = loteRepository.findByProdutoIdOrderByDataDeValidadeAsc(id);
		for(Lote lote: lotes) {
			int qtdItem = lote.getNumeroDeItens();
			if(qtd == 0){
				break;
			} else if(qtd > qtdItem) {
				qtd -= qtdItem;
				loteRepository.delete(lote);
			}else if(qtd == qtdItem){
				loteRepository.delete(lote);
				break;
			} else {
				qtdItem -= qtd;
				lote.setNumeroDeItens(qtdItem);
				loteRepository.save(lote);
				break;
			}
		}

	}
}
