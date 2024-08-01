package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroItem {
    public static ResponseEntity<?> itemNaoEncontradoCarrinho() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto não encontrado no carrinho"),
                HttpStatus.BAD_REQUEST);
    }
}
