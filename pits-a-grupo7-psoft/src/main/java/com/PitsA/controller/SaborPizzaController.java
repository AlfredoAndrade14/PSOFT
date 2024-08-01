package com.PitsA.controller;

import com.PitsA.dto.SaborPizzaDTO;
import com.PitsA.dto.SaborPizzaFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.PitsA.exception.estabelecimento.ThereIsNoPizzaFlavorException;
import com.PitsA.exception.estabelecimento.ThereIsNoPizzaFlavorWithTypeException;
import com.PitsA.exception.saborPizza.*;
import com.PitsA.exception.tamanhoPizza.TamanhoPizzaNotFoundException;
import com.PitsA.exception.tipoSabor.TipoSaborNotFoundException;
import com.PitsA.service.SaborPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saborPizza")
@CrossOrigin
public class SaborPizzaController {

    @Autowired
    private SaborPizzaService saborPizzaService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> pegaSabor(@PathVariable("id") Long id) throws SaborPizzaNotFoundException {
            SaborPizzaDTO saborPizza = this.saborPizzaService.pegaPeloId(id);
            return  new ResponseEntity<>(saborPizza, HttpStatus.OK);
    }

    @PostMapping(value = "/estabelecimento/{idEstabelecimento}")
    public ResponseEntity<?> criaSabor(@PathVariable("idEstabelecimento") Long idEstabelecimento, @RequestBody SaborPizzaFormDTO saborPizzaDTO, @RequestParam Integer codigoAcesso) throws SaborPizzaMustHaveAValidNameException, SaborPizzaMustHaveAValidValorException, SaborPizzaMustHaveAValidTipoSaborException, SaborPizzaMustHaveAValidTamanhoException, EstabelecimentoNotFoundException, TamanhoPizzaNotFoundException, TipoSaborNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
            SaborPizzaDTO saborPizza = this.saborPizzaService.criaSabor(idEstabelecimento, saborPizzaDTO, codigoAcesso);
            return new ResponseEntity<>(saborPizza,HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editaSabor(@PathVariable("id") Long id, @RequestBody SaborPizzaFormDTO saborPizzaDTO) throws SaborPizzaMustHaveAValidNameException, SaborPizzaMustHaveAValidValorException, SaborPizzaNotFoundException, SaborPizzaMustHaveAValidTipoSaborException, SaborPizzaMustHaveAValidTamanhoException, TamanhoPizzaNotFoundException, TipoSaborNotFoundException {
            SaborPizzaDTO saborPizza = this.saborPizzaService.editaSabor(id, saborPizzaDTO);
            return new ResponseEntity<>(saborPizza, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletaSabor(@PathVariable("id") Long id) throws SaborPizzaNotFoundException {
            this.saborPizzaService.deletaSabor(id);
            return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping(value = "/{idSaborPizza}/disponibilidadeSabor/{idEstabelecimento}")
    public ResponseEntity<?> atualizaDisponibilidade(@PathVariable("idEstabelecimento") Long idEstabelecimento, @PathVariable("idSaborPizza") Long idSaborPizza, @RequestParam Boolean disponibilidade, @RequestParam Integer codigoAcesso) throws EstabelecimentoNotFoundException, SaborPizzaNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, InvalidFlavorForStore {
        this.saborPizzaService.atualizaDisponibilidadeSaborPizza(idEstabelecimento, idSaborPizza, disponibilidade, codigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cardapio/{idEstabelecimento}")
    public ResponseEntity<?> pegaSaborPorEstabelecimento(@PathVariable("idEstabelecimento") Long idEstabelecimento, @RequestParam(required = false) String tipoSabor, @RequestParam Integer codigoAcesso) throws EstabelecimentoNotFoundException, ThereIsNoPizzaFlavorWithTypeException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
        List<SaborPizzaDTO> saborPizzaDTOSet = this.saborPizzaService.pegaPorEstabelecimento(idEstabelecimento, tipoSabor, codigoAcesso);
        return new ResponseEntity<>(saborPizzaDTOSet, HttpStatus.OK);
    }
}
