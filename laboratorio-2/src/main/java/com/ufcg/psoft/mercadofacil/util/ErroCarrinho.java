package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCarrinho extends Throwable {
    public static ResponseEntity<CustomErrorType> erroCarrinhoVazio() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType("O cliente não possui carrinho ativo"),
                HttpStatus.NOT_FOUND);
    }
}
