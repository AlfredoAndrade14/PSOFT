package com.PitsA.util;

import com.PitsA.exception.tamanhoPizza.TamanhoPizzaMustHaveAValidTamanhoException;
import com.PitsA.exception.tamanhoPizza.TamanhoPizzaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroTamanhoPizza {
    static final String TAMANHO_PIZZA_NAO_CADASTRADO = "Tamanho de pizza não cadastrado.";
    static final String TAMANHO_PIZZA_TAMANHO_INVALIDO = "Tamanho pizza precisa de um tamanho válido.";

    @ExceptionHandler(TamanhoPizzaNotFoundException.class)
    public static ResponseEntity<CustomErrorType> erroTamanhoNaoCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(ErroTamanhoPizza.TAMANHO_PIZZA_NAO_CADASTRADO),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TamanhoPizzaMustHaveAValidTamanhoException.class)
    public static ResponseEntity<CustomErrorType> erroTamanhoPizzaTamanhoInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroTamanhoPizza.TAMANHO_PIZZA_TAMANHO_INVALIDO), HttpStatus.NOT_ACCEPTABLE);
    }
}
