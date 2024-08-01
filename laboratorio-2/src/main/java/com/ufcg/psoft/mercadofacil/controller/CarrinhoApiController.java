package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.exception.IllegalArgumentException;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {
    @Autowired
    CarrinhoService carrinhoService;

    @PostMapping(value = "/cliente/{id}/carrinho")
    public ResponseEntity<?> adicionarItemCarrinho(@PathVariable("id") long id, @RequestBody ItemCompraDTO itemCompraDTO) {

        try {
            CarrinhoDTO carrinho = carrinhoService.adicionarItemCarrinho(id, itemCompraDTO);
            return new ResponseEntity<CarrinhoDTO>(carrinho, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        } catch(ProdutoNotFoundException e) {
            return ErroProduto.erroProdutoNaoEnconrtrado(itemCompraDTO.getProdutoId());
        } catch (ProdutoSoldOutException e){
            return ErroProduto.erroProdutoEsgotado(itemCompraDTO.getProdutoId());
        } catch (InvalidQuantityException e) {
            return ErroQtdInvalida.erroQtdInvalida();
        } catch (fewerProdutoException e) {
            return ErroProduto.erroMenosProduto(itemCompraDTO.getProdutoId(), itemCompraDTO.getQtd());
        } catch (IllegalArgumentException e) {
            return ErroIllegalArgument.nullArgument();
        }
    }

    @PutMapping(value = "/cliente/{id}/carrinho")
    public ResponseEntity<?> removeItemCarrinho(@PathVariable("id") long id, @RequestBody long projectId) {

        try {
            CarrinhoDTO carrinho = carrinhoService.removeItemCarrinho(id, projectId);
            return new ResponseEntity<CarrinhoDTO>(carrinho, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        } catch (ItemNotFoundException e) {
            return ErroItem.itemNaoEncontradoCarrinho();
        } catch (ErroCarrinho e) {
            return ErroCarrinho.erroCarrinhoVazio();
        } catch (CarrinhoVazioException e) {
            return ErroCarrinho.erroCarrinhoVazio();
        }
    }

    @DeleteMapping(value = "/cliente/{id}/carrinho")
    public ResponseEntity<?> removerCarrinho(@PathVariable("id") long id) {

        try {
            carrinhoService.removerCarrinho(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        } catch (ErroCarrinho e) {
            return ErroCarrinho.erroCarrinhoVazio();
        }
    }
}
