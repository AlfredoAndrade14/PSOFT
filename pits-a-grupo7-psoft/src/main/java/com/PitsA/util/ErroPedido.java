package com.PitsA.util;

import com.PitsA.exception.pedido.ClientMustBeThePedidosOwnerException;
import com.PitsA.exception.pedido.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroPedido {

    final static String QUANTIDADE_INVALIDA = "O pedido precisa ter uma quantidade válida.";
    final static String UM_SABOR_NECESSARIO = "O pedido precisa ter pelo menos um sabor.";
    final static String TAMANHO_INVALIDO = "Ambos sabores precisam conter o tamanho igual a 'Grande'.";
    final static String PEDIDO_NAO_ENCONTRADO = "O pedido não foi encontrado.";
    final static String TIPO_PAGAMENTO_INVALIDO = "O tipo de pagamento informado não é válido.";
    final static String TRANSICAO_DE_STATUS_NAO_PERMITIDO = "Esta transição de status não é possivel no momento,";
    static final String CLIENTE_DIFERENTE = "Somente o dono do pedido pode cancelar o pedido.";
    static final String CANCELAMENTO_INVALIDO = "Não é possível cancelar um pedido após estar pronto.";
    static final String ENTREGADOR_NAO_ACEITO = "Entregador precisa ser aceito para realizar uma entrega.";
    static final String ENTREGADOR_EM_DESCANSO = "Entregador não pode realizar entregas enquanto está em descanso.";

    @ExceptionHandler(InvalidQuantityInOrderException.class)
    public ResponseEntity<CustomErrorType> erroQuantidadeInvalida() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.QUANTIDADE_INVALIDA), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MustExistAtLeastOneFlavorException.class)
    public ResponseEntity<CustomErrorType> erroSaborNecessario() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.UM_SABOR_NECESSARIO), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TheFlavorSizeMustBeGrandeForHalfPizzaException.class)
    public ResponseEntity<CustomErrorType> erroTamanhoInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.TAMANHO_INVALIDO), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<CustomErrorType> erroPedidoNaoEncontrado() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.PEDIDO_NAO_ENCONTRADO), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTipoPagamentoException.class)
    public ResponseEntity<CustomErrorType> erroTipoPagamentoInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.TIPO_PAGAMENTO_INVALIDO), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidStatusTransition.class)
    public ResponseEntity<CustomErrorType> erroTransicaoStatus() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.TRANSICAO_DE_STATUS_NAO_PERMITIDO), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientMustBeThePedidosOwnerException.class)
    public static ResponseEntity<CustomErrorType> erroClienteNaoPossuiPedido() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.CLIENTE_DIFERENTE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImpossibleToCancelAReadyPedido.class)
    public static ResponseEntity<CustomErrorType> erroCancelamentoPedidoPronto() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.CANCELAMENTO_INVALIDO), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntregadorMustBeAcceptedException.class)
    public static ResponseEntity<CustomErrorType> erroEntregadorNaoAceito() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.ENTREGADOR_NAO_ACEITO), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntregadorMustBeAtivoException.class)
    public static ResponseEntity<CustomErrorType> erroEntregadorEmDescanso() {
        return new ResponseEntity<>(new CustomErrorType(ErroPedido.ENTREGADOR_EM_DESCANSO), HttpStatus.BAD_REQUEST);
    }
}
