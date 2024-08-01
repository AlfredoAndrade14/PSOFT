package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoSoldOutException;
import com.ufcg.psoft.mercadofacil.exception.fewerProdutoException;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {
    @Autowired
    CompraRepository compraRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    LoteService loteService;

    @Autowired
    PagamentoService pagamentoService;

    @Autowired
    public ModelMapper modelMapper;


    public void salvaCompra(Compra compra) {
        compraRepository.save(compra);
    }

    public CompraDTO compraCarrinho(long id) throws ClienteNotFoundException, CarrinhoVazioException, ProdutoSoldOutException, fewerProdutoException {
        ClienteDTO cliente = clienteService.getClienteById(id);
        Carrinho carrinho = verificaCarrinho(cliente);

        double preco = compraECalculaPreco(carrinho);

        clienteService.setCarrinho(cliente.getId(),null);

        Compra compra = geraCompraEPagamento(id, carrinho, preco);
        return modelMapper.map(compra, CompraDTO.class);
    }

    private Carrinho verificaCarrinho(ClienteDTO cliente) throws CarrinhoVazioException {
        if (cliente.getCarrinho() == null) {
            throw new CarrinhoVazioException();
        }
       return cliente.getCarrinho();
    }

    private double compraECalculaPreco(Carrinho carrinho) throws ProdutoSoldOutException, fewerProdutoException {
        double preco = 0;
        for (ItemCompra item: carrinho.getItens()){
            loteService.compraProduto(item.getProduto().getId(),item.getQtd());
            preco += (item.getQtd() *item.getProduto().getPreco());
        }
        return preco;
    }

    private Compra geraCompraEPagamento(Long id, Carrinho carrinho, double preco) throws ClienteNotFoundException {
        Compra compra = new Compra(modelMapper.map(clienteService.getClienteById(id), Cliente.class),carrinho);
        salvaCompra(compra);
        pagamentoService.salvaPagamento(new Pagamento(modelMapper.map(clienteService.getClienteById(id), Cliente.class), compra, preco));
        return compra;
    }

    public List<CompraDTO> listaCompras(long id) throws ClienteNotFoundException {
        clienteService.getClienteById(id);
        return compraRepository.getByClienteId(id).stream()
                .map(compra -> modelMapper.map(compra, CompraDTO.class))
                .collect(Collectors.toList());
    }
}
