package com.PitsA.controller;

import com.PitsA.dto.ClienteDTO;
import com.PitsA.dto.ClienteFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.cliente.ClienteMustHaveAValidAddressException;
import com.PitsA.exception.cliente.ClienteMustHaveAValidNameException;
import com.PitsA.exception.cliente.ClienteNotFoundException;
import com.PitsA.exception.pedido.ClientMustBeThePedidosOwnerException;
import com.PitsA.exception.pedido.ImpossibleToCancelAReadyPedido;
import com.PitsA.exception.pedido.PedidoNotFoundException;
import com.PitsA.exception.saborPizza.SaborPizzaIsAvailableException;
import com.PitsA.exception.saborPizza.SaborPizzaNotFoundException;
import com.PitsA.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody ClienteFormDTO clienteFormDTO) throws MustHaveAnAccessCodeException, ClienteMustHaveAValidAddressException, ClienteMustHaveAValidNameException, TheAccessCodeMustHaveSixDigitsException {
        ClienteDTO cliente = clienteService.criarCliente(clienteFormDTO);
        return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaCliente(@PathVariable("id") Long id, @RequestBody ClienteFormDTO clienteDTO, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, ClienteMustHaveAValidAddressException, ClienteMustHaveAValidNameException, AccessCodeIncorrectException, ClienteNotFoundException, TheAccessCodeMustHaveSixDigitsException {
        ClienteDTO cliente = clienteService.atualizarCliente(id, clienteDTO, codigoAcesso);
        return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizaSenha(@PathVariable("id") Long id, @RequestParam Integer codigoAcesso, @RequestParam Integer novoCodigoAcesso) throws MustHaveAnAccessCodeException, AccessCodeIncorrectException, ClienteNotFoundException, TheAccessCodeMustHaveSixDigitsException {
        clienteService.atualizaSenha(id, codigoAcesso, novoCodigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable("id") Long id, @RequestParam Integer codigoAcesso) throws ClienteNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
       clienteService.removeCliente(id, codigoAcesso);
       return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> pesquisaCliente(@PathVariable("id") Long id) throws ClienteNotFoundException{
        ClienteDTO cliente = clienteService.getClienteById(id);
        return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
    }

    @PutMapping("/{id}/saborPizza/interesse/{idSaborPizza}")
    public ResponseEntity<?> demonstraInteresseSaborPizza(@PathVariable("id") Long id, @PathVariable("idSaborPizza") Long idSaborPizza) throws SaborPizzaNotFoundException, SaborPizzaIsAvailableException, ClienteNotFoundException {
        this.clienteService.demonstraInteresseSaborPizza(id, idSaborPizza);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/cancelar/pedido/{idPedido}")
    public ResponseEntity<?> cancelarPedido(@PathVariable("id") Long id, @PathVariable("idPedido") Long idPedido, @RequestBody Integer codigoAcesso) throws ClienteNotFoundException, MustHaveAnAccessCodeException, ClientMustBeThePedidosOwnerException, ImpossibleToCancelAReadyPedido, PedidoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
        this.clienteService.cancelaPedido(id, idPedido, codigoAcesso);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}