package com.PitsA.util;

import com.PitsA.exception.cliente.ClienteMustHaveAValidAddressException;
import com.PitsA.exception.cliente.ClienteMustHaveAValidNameException;
import com.PitsA.exception.cliente.ClienteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroCliente {

    static final String CLIENTE_NAO_CADASTRADO = "Cliente não está cadastrado.";
    static final String NOME_INVALIDO = "Cliente precisa de um nome válido.";
    static final String ENDERECO_INVALIDO = "Cliente precisa de um endereço válido.";


    @ExceptionHandler(ClienteNotFoundException.class)
    public static ResponseEntity<CustomErrorType> erroClienteNaoCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(ErroCliente.CLIENTE_NAO_CADASTRADO),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteMustHaveAValidNameException.class)
    public static ResponseEntity<CustomErrorType> erroNomeInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroCliente.NOME_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteMustHaveAValidAddressException.class)
    public static ResponseEntity<CustomErrorType> erroEnderecoInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroCliente.ENDERECO_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }



}
