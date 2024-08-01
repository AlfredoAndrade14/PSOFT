package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.util.ErroQtdInvalida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteApiController {

	@Autowired
	LoteService loteService;
	
	@Autowired
	ProdutoService produtoService;
	
	@GetMapping(value = "/lotes")
	public ResponseEntity<?> listarLotes() {
		
		List<LoteDTO> lotes = loteService.listarLotes();

		if (lotes.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		}
		
		return new ResponseEntity<List<LoteDTO>>(lotes, HttpStatus.OK);
	}

	@GetMapping(value = "/lote/{id}")
	public ResponseEntity<?> consultaLote(@PathVariable("id") long id) {

		try {
			LoteDTO lote = loteService.getLoteById(id);
			return new ResponseEntity<LoteDTO>(lote, HttpStatus.OK);
		} catch (LoteNotFoundException e) {
			return ErroLote.erroLoteNaoEnconrtrado(id);
		}
	}

	@PatchMapping(value = "/lote/{id}")
	public ResponseEntity<?> atualizarQtdLote(@PathVariable("id") long id, @RequestBody int numItens) {

		try {
			LoteDTO lote = loteService.atualizaQtdLote(id, numItens);
			return new ResponseEntity<LoteDTO>(lote, HttpStatus.OK);
		} catch (LoteNotFoundException e) {
			return ErroLote.erroLoteNaoEnconrtrado(id);
		} catch (ErroQtdInvalida e) {
			return ErroQtdInvalida.erroQtdInvalida();
		}
	}

	@PostMapping(value = "/produto/{idProduto}/lote/")
	public ResponseEntity<?> criarLote(@RequestBody LoteDTO loteDTO) {
			
		try {
			LoteDTO lote = loteService.criaLote(loteDTO);
			return new ResponseEntity<LoteDTO>(lote, HttpStatus.CREATED);
		} catch (ProdutoNotFoundException e) {
			return ErroProduto.erroProdutoNaoEnconrtrado(loteDTO.getProdutoId());
		} catch (ErroQtdInvalida e) {
			return ErroQtdInvalida.erroQtdInvalida();
		}
	}

    @DeleteMapping(value = "/lote/{id}")
    public ResponseEntity<?> removerLote(@PathVariable("id") long id) {

        try {
            loteService.removerLoteCadastrado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LoteNotFoundException e) {
            return ErroLote.erroLoteNaoEnconrtrado(id);
        }
    }
}