package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.exception.IllegalArgumentException;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.util.ErroCarrinho;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {
    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    ItemCompraService itemCompraService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public CompraService compraService;

    public void salvaCarrinho(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }


    public CarrinhoDTO adicionarItemCarrinho(long id, ItemCompraDTO item) throws ClienteNotFoundException, ProdutoNotFoundException, ProdutoSoldOutException, InvalidQuantityException, fewerProdutoException, IllegalArgumentException {
        ClienteDTO cliente = verificaCarrinho(id);
        Carrinho carrinho = cliente.getCarrinho();

        boolean temItem = verificaItem(carrinho, item) ;

        if(!temItem) {
            cliente.getCarrinho().addItem(itemCompraService.criaItemCompra(item));
        }

        salvaCarrinho(cliente.getCarrinho());
        clienteService.atualizaCliente(id, cliente);
        return modelMapper.map(cliente.getCarrinho(), CarrinhoDTO.class);
    }

    private ClienteDTO verificaCarrinho(Long id) throws ClienteNotFoundException {
        ClienteDTO cliente = clienteService.getClienteById(id);
        if(cliente.getCarrinho() == null) {
            Carrinho carrinho = new Carrinho();
            salvaCarrinho(carrinho);
            clienteService.setCarrinho(cliente.getId(), carrinho);
            return clienteService.getClienteById(id);
        }
        return cliente;
    }

    private boolean verificaItem(Carrinho carrinho, ItemCompraDTO item) throws ProdutoSoldOutException, fewerProdutoException {
        for(ItemCompra itemCarrinho : carrinho.getItens()){
            if(itemCarrinho.getProduto().getId().equals(item.getProdutoId())){
                itemCarrinho.setQtd(item.getQtd());
                itemCompraService.alteraQtdItem(itemCarrinho);
                return true;
            }
        }
        return false;
    }

    public CarrinhoDTO removeItemCarrinho(long id, long produtoId) throws ClienteNotFoundException, ItemNotFoundException, ErroCarrinho, CarrinhoVazioException {
        ClienteDTO cliente = clienteService.getClienteById(id);
        Carrinho carrinho = cliente.getCarrinho();

        if(carrinho == null){
            throw new CarrinhoVazioException();
        }

        int index = indexItem(produtoId, carrinho);

        if(index != -1) {
            return removeItem(index, carrinho, cliente);
        } else {
            throw new ItemNotFoundException();
        }
    }

    private CarrinhoDTO removeItem(int index, Carrinho carrinho, ClienteDTO cliente) throws ClienteNotFoundException, ErroCarrinho {
        ItemCompra item = carrinho.getItens().get(index);
        carrinho.getItens().remove(index);
        salvaCarrinho(carrinho);
        itemCompraService.removerItem(item);
        if(carrinho.getItens().size() == 0){
            removerCarrinho(cliente.getId());
            return null;
        }

        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

    private int indexItem(long produtoId, Carrinho carrinho) {
        for(ItemCompra itemCarrinho : carrinho.getItens()){
            if(itemCarrinho.getProduto().getId() == produtoId){

                return carrinho.getItens().indexOf(itemCarrinho);
            }
        }
        return -1;
    }

    public void removerCarrinho(long id) throws ClienteNotFoundException, ErroCarrinho {
        ClienteDTO cliente = clienteService.getClienteById(id);
        if(cliente.getCarrinho() == null){
            throw new ErroCarrinho();
        }
        clienteService.setCarrinho(cliente.getId(),null);
        carrinhoRepository.delete(cliente.getCarrinho());
    }
}
