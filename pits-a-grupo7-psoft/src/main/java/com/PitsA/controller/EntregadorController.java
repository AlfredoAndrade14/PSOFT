package com.PitsA.controller;

import com.PitsA.dto.EntregadorDTO;
import com.PitsA.dto.EntregadorFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.entregador.*;
import com.PitsA.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.PitsA.exception.estabelecimento.ThereIsNoDeliveryPersonAcceptedExcetion;
import com.PitsA.exception.pedido.EntregadorMustBeAcceptedException;
import com.PitsA.exception.pedido.EntregadorMustBeAtivoException;
import com.PitsA.exception.pedido.PedidoNotFoundException;
import com.PitsA.exception.tipoVeiculo.TipoVeiculoNotFoundException;
import com.PitsA.service.EntregadorService;
import com.PitsA.util.ENUM.DisponibilidadeEntregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/entregador")
@CrossOrigin
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @PostMapping(value = "/estabelecimento/{idEstabelecimento}")
    public ResponseEntity<?> criarEntregador(@PathVariable("idEstabelecimento") Long idEstabelecimento, @RequestBody EntregadorFormDTO entregadorDTO) throws MustHaveAnAccessCodeException, EntregadorMustHaveAValidVehicleColorException, EstabelecimentoNotFoundException, EntregadorMustHaveAValidVehicleTypeException, EntregadorMustHaveAValidVehiclePlateException, EntregadorMustHaveAValidNameException, TheAccessCodeMustHaveSixDigitsException, TipoVeiculoNotFoundException {
        EntregadorDTO entregador = entregadorService.criarEntregador(idEstabelecimento, entregadorDTO);
        return new ResponseEntity<EntregadorDTO>(entregador, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(@PathVariable("id") Long id, @RequestBody EntregadorFormDTO entregadorDTO, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, EntregadorMustHaveAValidVehicleColorException, EntregadorMustHaveAValidVehicleTypeException, AccessCodeIncorrectException, EntregadorMustHaveAValidVehiclePlateException, EntregadorMustHaveAValidNameException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, TipoVeiculoNotFoundException {
        EntregadorDTO entregador = this.entregadorService.atualizarEntregador(id, codigoAcesso, entregadorDTO);
        return new ResponseEntity<EntregadorDTO>(entregador, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/disponibilidade/")
    public ResponseEntity<?> atualizaDisponibilidade(@PathVariable("id") Long id, @RequestParam DisponibilidadeEntregador disponibilidade, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, EntregadorMustHaveAValidVehicleColorException, EntregadorMustHaveAValidVehicleTypeException, AccessCodeIncorrectException, EntregadorMustHaveAValidVehiclePlateException, EntregadorMustHaveAValidNameException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, TipoVeiculoNotFoundException, EstabelecimentoNotFoundException, NotApprovedEntregador, PedidoNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        EntregadorDTO entregador = this.entregadorService.atualizarDisponibilidade(id, codigoAcesso, disponibilidade);
        return new ResponseEntity<EntregadorDTO>(entregador, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizaSenha(@PathVariable("id") Long id, @RequestParam Integer codigoAcesso, @RequestParam Integer novoCodigoAcesso) throws MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException {
        this.entregadorService.atualizarSenha(id, codigoAcesso, novoCodigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeEntregador(@PathVariable("id") Long id, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException {
        this.entregadorService.removeEntregador(id,codigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/estabelecimento/{idEstabelecimento}")
    public ResponseEntity<?> listaEntregadoresEstabelecimento(@PathVariable("idEstabelecimento") Long idEstabelecimento) throws EstabelecimentoNotFoundException, ThereIsNoDeliveryPersonAcceptedExcetion {
        Set<EntregadorDTO> entregadores = entregadorService.getEntregadoresEstabelecimento(idEstabelecimento);
        return new ResponseEntity<Set<EntregadorDTO>>(entregadores, HttpStatus.OK);
    }
}