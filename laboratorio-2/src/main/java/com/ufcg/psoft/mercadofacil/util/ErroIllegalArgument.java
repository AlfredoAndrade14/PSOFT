package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroIllegalArgument extends Throwable {

    public static ResponseEntity<?> nullArgument() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType("Argumento não pode ser nulo"), HttpStatus.BAD_REQUEST);
    }
}
