package com.PitsA.controller;

import com.PitsA.dto.PedidoDTO;
import com.PitsA.dto.PedidoFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.cliente.ClienteNotFoundException;
import com.PitsA.exception.entregador.EntregadorNotFoundException;
import com.PitsA.exception.entregador.NotApprovedEntregador;
import com.PitsA.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.PitsA.exception.pedido.*;
import com.PitsA.exception.saborPizza.SaborPizzaNotFoundException;
import com.PitsA.exception.saborPizza.SaborPizzaUnavailableException;
import com.PitsA.service.PedidoService;
import com.PitsA.util.ENUM.TipoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping(value = "/pedido/cliente/{clienteId}/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<?> criarPedido(@PathVariable("clienteId") Long clienteId, @PathVariable("estabelecimentoId") Long estabelecimentoId, @RequestParam Integer codigoAcessoCliente, @RequestBody PedidoFormDTO pedidoDTO) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, ClienteNotFoundException, TheAccessCodeMustHaveSixDigitsException, MustExistAtLeastOneFlavorException, InvalidQuantityInOrderException, SaborPizzaNotFoundException, TheFlavorSizeMustBeGrandeForHalfPizzaException, SaborPizzaUnavailableException {
        PedidoDTO pedido = pedidoService.criarPedido(clienteId, estabelecimentoId, pedidoDTO, codigoAcessoCliente);
        return new ResponseEntity<PedidoDTO>(pedido, HttpStatus.CREATED);
    }

    @PutMapping("/pedido/{id}/confirmar/{clienteId}")
    public ResponseEntity<?> confirmaPedido(@PathVariable("clienteId") Long clienteId, @PathVariable("id") Long pedidoId, @RequestParam TipoPagamento tipoPagamento, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, InvalidTipoPagamentoException, PedidoNotFoundException, AccessCodeIncorrectException, ClienteNotFoundException, TheAccessCodeMustHaveSixDigitsException, InvalidStatusTransition, EstabelecimentoNotFoundException, NotApprovedEntregador, EntregadorNotFoundException {
        PedidoDTO pedido = this.pedidoService.confirmaPedido(clienteId, pedidoId, tipoPagamento, codigoAcesso);
        return new ResponseEntity<PedidoDTO>(pedido, HttpStatus.OK);
    }

    @PutMapping("/pedido/{id}/finalizaPreparo/{estabelecimentoId}")
    public ResponseEntity<?> finalizaPreparo(@PathVariable("estabelecimentoId") Long estabelecimentoId, @PathVariable("id") Long pedidoId, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, PedidoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EstabelecimentoNotFoundException, InvalidStatusTransition, NotApprovedEntregador, EntregadorNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        PedidoDTO pedido = this.pedidoService.finalizaPreparo(estabelecimentoId, pedidoId, codigoAcesso);
        return new ResponseEntity<PedidoDTO>(pedido, HttpStatus.OK);
    }

    @PutMapping("/pedido/{id}/confirmaRecebimento/{clienteId}")
    public ResponseEntity<?> confirmaRecebimento(@PathVariable("clienteId") Long clienteId, @PathVariable("id") Long pedidoId, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, PedidoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, InvalidStatusTransition, ClienteNotFoundException {
        PedidoDTO pedido = this.pedidoService.confirmaRecebimento(clienteId, pedidoId, codigoAcesso);
        return new ResponseEntity<PedidoDTO>(pedido, HttpStatus.OK);
    }

    @GetMapping(value = "/pedidos/cliente/{clienteId}")
    public ResponseEntity<?> listaPedidosCliente(@PathVariable("clienteId") Long clienteId, @RequestParam(required = false) String status, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, ClienteNotFoundException {
        List<PedidoDTO> pedidos = pedidoService.getPedidos(clienteId, status, codigoAcesso);
        return new ResponseEntity<List<PedidoDTO>>(pedidos, HttpStatus.OK);
    }

    @GetMapping(value = "/pedido/{id}/cliente/{clienteId}")
    public ResponseEntity<?> listaPedidoCliente(@PathVariable("clienteId") Long clienteId, @PathVariable("id")  Long pedidoId, @RequestParam Integer codigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, ClienteNotFoundException, PedidoNotFoundException {
        PedidoDTO pedidos = pedidoService.getPedido(clienteId, pedidoId, codigoAcesso);
        return new ResponseEntity<PedidoDTO>(pedidos, HttpStatus.OK);
    }


}
