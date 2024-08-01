package com.PitsA.util;

import com.PitsA.exception.entregador.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroEntregador {

    static final String NOME_INVALIDO = "Entregador precisa de um nome válido.";
    static final String COR_VICULO_INVALIDA = "Entregador precisa de um cor de veiculo válida.";
    static final String PLACA_INVALIDA = "Entregador precisa de uma placa de veiculo válida.";
    static final String TIPO_DE_VEICULO_INVALIDA = "Entregador precisa de um tipo de veiculo válido.";
    static final String ENTREGADOR_NAO_CADASTRADO = "Entregador não está cadastrado.";
    static final String ENTREGADOR_NAO_APROVADO = "Entregador não está aprovado para este restaurante.";

    @ExceptionHandler(EntregadorMustHaveAValidNameException.class)
    public static ResponseEntity<CustomErrorType> erroNomeInvalido() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.NOME_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntregadorMustHaveAValidVehicleColorException.class)
    public static ResponseEntity<CustomErrorType> erroCorInvalida() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.COR_VICULO_INVALIDA),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntregadorMustHaveAValidVehiclePlateException.class)
    public static ResponseEntity<CustomErrorType> erroPlacaInvalida() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.PLACA_INVALIDA),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntregadorMustHaveAValidVehicleTypeException.class)
    public static ResponseEntity<CustomErrorType> erroTipoVeiculoInvalido() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.TIPO_DE_VEICULO_INVALIDA),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntregadorNotFoundException.class)
    public static ResponseEntity<CustomErrorType> erroEntregadorNaoCadastrado() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.ENTREGADOR_NAO_CADASTRADO),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotApprovedEntregador.class)
    public static ResponseEntity<CustomErrorType> entregadorNaoAprovado() {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.ENTREGADOR_NAO_APROVADO),
                HttpStatus.BAD_REQUEST);
    }
}
