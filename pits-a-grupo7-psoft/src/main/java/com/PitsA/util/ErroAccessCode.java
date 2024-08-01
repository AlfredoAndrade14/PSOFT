package com.PitsA.util;

import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroAccessCode {
    static final String CODIGO_DE_ACESSO_SEIS_DIGITOS = "Código de acesso precisa ter 6 (seis) digitos.";
    static final String CODIGO_DE_ACESSO_INCORRETO = "O código de acesso informado está incorreto.";
    static final String CODIGO_ACESSO_NECESSARIO = "É necessário informar um código de acesso.";

    @ExceptionHandler(TheAccessCodeMustHaveSixDigitsException.class)
    public static ResponseEntity<CustomErrorType> erroCodigoSeisDigitos() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroAccessCode.CODIGO_DE_ACESSO_SEIS_DIGITOS),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessCodeIncorrectException.class)
    public static ResponseEntity<CustomErrorType> erroCodigoAcessoIncorreto() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroAccessCode.CODIGO_DE_ACESSO_INCORRETO),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MustHaveAnAccessCodeException.class)
    public static ResponseEntity<CustomErrorType> erroCodigoAcessoInvalido() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroAccessCode.CODIGO_ACESSO_NECESSARIO),
                HttpStatus.BAD_REQUEST);
    }
}
