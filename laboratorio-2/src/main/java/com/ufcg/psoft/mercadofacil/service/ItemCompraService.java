package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.InvalidQuantityException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoSoldOutException;
import com.ufcg.psoft.mercadofacil.exception.fewerProdutoException;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ItemCompraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCompraService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LoteService loteService;

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    @Autowired
    public ModelMapper modelMapper;


    public ItemCompra criaItemCompra(ItemCompraDTO item) throws ProdutoNotFoundException, ProdutoSoldOutException, InvalidQuantityException, fewerProdutoException {
        if(item.getQtd() <= 0) {
            throw new InvalidQuantityException();
        }
        Produto produto = produtoService.getProduto(item.getProdutoId());
        loteService.checaQuantidadeDisponivel(produto.getId(),item.getQtd());
        ItemCompra itemCriado = new ItemCompra(produto, item.getQtd());
        salvarItem(itemCriado);
        return itemCriado;
    }

    private void salvarItem(ItemCompra itemCriado) {
        itemCompraRepository.save(itemCriado);
    }

    public void alteraQtdItem(ItemCompra itemCarrinho) throws ProdutoSoldOutException, fewerProdutoException {
        loteService.checaQuantidadeDisponivel(itemCarrinho.getProduto().getId(),itemCarrinho.getQtd());
        salvarItem(itemCarrinho);
    }

    public void removerItem(ItemCompra item) {
        itemCompraRepository.delete(item);
    }
}
