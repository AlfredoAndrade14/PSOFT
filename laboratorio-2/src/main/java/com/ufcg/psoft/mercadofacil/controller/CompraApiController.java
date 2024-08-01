package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoSoldOutException;
import com.ufcg.psoft.mercadofacil.exception.fewerProdutoException;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.util.ErroCarrinho;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompraApiController {
    @Autowired
    private CompraService compraService;

    @PostMapping(value = "/cliente/{id}/compra")
    public ResponseEntity<?> compraCarrinho(@PathVariable("id") long id) {

        try {
            CompraDTO compra = compraService.compraCarrinho(id);
            return new ResponseEntity<CompraDTO>(compra, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        } catch (CarrinhoVazioException e) {
            return ErroCarrinho.erroCarrinhoVazio();
        } catch (ProdutoSoldOutException e) {
            return ErroProduto.erroProdutoEsgotado(e.getProdutoId());
        } catch (fewerProdutoException e) {
            return ErroProduto.erroMenosProduto(e.getProdutoId(), e.getQtd());
        }
    }

    @GetMapping(value = "/cliente/{id}/compras")
    public ResponseEntity<?> listaCarrinhos(@PathVariable("id") long id) {

        try {
            List<CompraDTO> compras = compraService.listaCompras(id);
            return new ResponseEntity<List<CompraDTO>>(compras, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        }
    }
}
