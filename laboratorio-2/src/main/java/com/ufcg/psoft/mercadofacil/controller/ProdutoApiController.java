package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.IllegalArgumentException;
import com.ufcg.psoft.mercadofacil.util.ErroIllegalArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoAlreadyCreatedException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoApiController {

	@Autowired
	ProdutoService produtoService;
	
	@GetMapping(value = "/produtos")
	public ResponseEntity<?> listarProdutos(@RequestParam(required = false) String nome) {
		try {
			List<ProdutoDTO> produtos;

			if(nome == null) {
				produtos = produtoService.listarProdutos();
			} else {
				produtos = produtoService.listarProdutos(nome);
			}

			if (produtos.isEmpty()) {
				return ErroProduto.erroSemProdutosCadastrados();
			}
			return new ResponseEntity<List<ProdutoDTO>>(produtos, HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return ErroIllegalArgument.nullArgument();
		}
	}
	
	@PostMapping(value = "/produto/")
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {

		try {
			ProdutoDTO produto = produtoService.criaProduto(produtoDTO);
			return new ResponseEntity<ProdutoDTO>(produto, HttpStatus.CREATED);
		} catch (ProdutoAlreadyCreatedException e) {
			return ErroProduto.erroProdutoJaCadastrado(produtoDTO);
		} catch (IllegalArgumentException e) {
			return ErroIllegalArgument.nullArgument();
		}
	}

	@GetMapping(value = "/produto/{id}")
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {

		try {
			ProdutoDTO produto = produtoService.getProdutoById(id);
			return new ResponseEntity<ProdutoDTO>(produto, HttpStatus.OK);
		} catch (ProdutoNotFoundException e) {
			return ErroProduto.erroProdutoNaoEnconrtrado(id);
		}
	}
	
	@PutMapping(value = "/produto/{id}")
	public ResponseEntity<?> atualizarProduto(@PathVariable("id") long id, @RequestBody ProdutoDTO produtoDTO) {

		try {
			ProdutoDTO produto = produtoService.atualizaProduto(id, produtoDTO);
			return new ResponseEntity<ProdutoDTO>(produto, HttpStatus.OK);
		} catch (ProdutoNotFoundException e) {
			return ErroProduto.erroProdutoNaoEnconrtrado(id);
		} catch (IllegalArgumentException e) {
			return ErroIllegalArgument.nullArgument();
		}
	}

	@DeleteMapping(value = "/produto/{id}")
	public ResponseEntity<?> removerProduto(@PathVariable("id") long id) {

		try {
			produtoService.removerProdutoCadastrado(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ProdutoNotFoundException e) {
			return ErroProduto.erroProdutoNaoEnconrtrado(id);
		}
	}
}