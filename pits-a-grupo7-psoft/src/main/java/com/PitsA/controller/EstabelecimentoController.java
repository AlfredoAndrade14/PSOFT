package com.PitsA.controller;

import com.PitsA.dto.DisponibilidadeSaborPizzaDTO;
import com.PitsA.dto.EstabelecimentoDTO;
import com.PitsA.dto.EstabelecimentoFormDTO;
import com.PitsA.dto.SaborPizzaDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.entregador.EntregadorNotFoundException;
import com.PitsA.exception.estabelecimento.*;
import com.PitsA.exception.saborPizza.SaborPizzaNotFoundException;
import com.PitsA.service.EstabelecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/estabelecimento")
@CrossOrigin
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @PostMapping
    public ResponseEntity<?> criarEstabelecimento(@RequestBody EstabelecimentoFormDTO estabelecimentoDTO) throws MustHaveAnAccessCodeException, EstabelecimentoMustHaveAValidAddressException, TheAccessCodeMustHaveSixDigitsException, EstabelecimentoMustHaveAValidNameException {
        EstabelecimentoDTO estabelecimento = estabelecimentoService.criarEstabelecimento(estabelecimentoDTO);
        return new ResponseEntity<EstabelecimentoDTO>(estabelecimento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaEstabelecimento(@PathVariable("id") Long id, @RequestBody EstabelecimentoFormDTO estabelecimentoDTO, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, EstabelecimentoMustHaveAValidAddressException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EstabelecimentoMustHaveAValidNameException {
        EstabelecimentoDTO estabelecimento = estabelecimentoService.atualizarEstabelecimento(id, estabelecimentoDTO, codigoAcesso);
        return new ResponseEntity<EstabelecimentoDTO>(estabelecimento, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizaSenha(@PathVariable("id") Long id, @RequestParam Integer codigoAcesso, @RequestParam Integer novoCodigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
        this.estabelecimentoService.atualizarSenha(id, codigoAcesso, novoCodigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/entregador/{entregadorId}")
    public ResponseEntity<?> aprovaEntregador(@PathVariable("id") Long id, @PathVariable("entregadorId") Long entregadorId, @RequestParam Boolean aprovado, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException {
        this.estabelecimentoService.aprovaEntregador(id, entregadorId, aprovado, codigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}