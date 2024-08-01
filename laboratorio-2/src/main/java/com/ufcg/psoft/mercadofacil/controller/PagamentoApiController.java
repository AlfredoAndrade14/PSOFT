package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.PagamentoDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.service.PagamentoService;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PagamentoApiController {
    @Autowired
    PagamentoService pagamentoService;

    @GetMapping(value = "/cliente/{id}/pagamentos")
    public ResponseEntity<?> listarPagamentos(@PathVariable("id") long id) {

        try {
            List<PagamentoDTO> pagamentos = pagamentoService.getPagamentosById(id);
            return new ResponseEntity<List<PagamentoDTO>>(pagamentos, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEnconrtrado(id);
        }
    }
}
