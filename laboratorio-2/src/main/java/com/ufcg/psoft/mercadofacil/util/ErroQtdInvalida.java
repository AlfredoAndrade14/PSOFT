package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroQtdInvalida extends Throwable {
    public static ResponseEntity<?> erroQtdInvalida() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType("Quantidade Invalida"),
                HttpStatus.BAD_REQUEST);
    }
}
