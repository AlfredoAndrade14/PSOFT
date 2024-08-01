package com.PitsA.util;

import com.PitsA.exception.tipoSabor.TipoSaborMustHaveAValidTipoException;
import com.PitsA.exception.tipoSabor.TipoSaborNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroTipoSabor {

    static final String TIPO_SABOR_NAO_CADASTRADO= "Tipo de sabor de pizza não está cadastrado.";
    static final String TIPO_SABOR_TIPO_INVALIDO = "Tipo de sabor da pizza precisa de um tipo válido.";

    @ExceptionHandler(TipoSaborNotFoundException.class)
    public static ResponseEntity<CustomErrorType> erroTipoSaborNaoCadastrado(){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroTipoSabor.TIPO_SABOR_NAO_CADASTRADO),
        HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoSaborMustHaveAValidTipoException.class)
    public ResponseEntity<CustomErrorType> erroTipoSaborTipoInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroTipoSabor.TIPO_SABOR_TIPO_INVALIDO), HttpStatus.NOT_ACCEPTABLE);
    }


}