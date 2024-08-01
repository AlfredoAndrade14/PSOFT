package com.PitsA.util;

import com.PitsA.exception.estabelecimento.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroEstabelecimento {

    static final String EXISTE_UM_ESTABELECIMENTO_ATIVO = "Não é possível cadastrar um estabelecimento enquanto há outro ativo.";
    static final String ESTABELECIMENTO_NAO_CADASTRADO = "Estabelecimento não está cadastrado.";
    static final String NOME_INVALIDO = "Estabelecimento precisa de um nome válido.";
    static final String ENDERECO_INVALIDO = "Estabelecimento precisa de um endereço válido.";
    static final String SEM_ENTREGADORES_APROVADOS = "Estabelecimento não possui entregadores.";
    static final String SEM_SABORES_DE_PIZZA = "Estabelecimento não possui sabores de pizza cadastrados.";
    static final String SEM_SABORES_DE_PIZZA_COM_TIPO = "Estabelecimento não possui sabores de pizza com tipo solicitado cadastrados";

    @ExceptionHandler(ThereIsNoDeliveryPersonAcceptedExcetion.class)
    public static ResponseEntity<CustomErrorType> erroEstabelecimentoSemEntregadoresAprovados(){
        return new ResponseEntity<>(
                new CustomErrorType(ErroEstabelecimento.SEM_ENTREGADORES_APROVADOS), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ThereMustBeOnlyOneActiveEstabelecimentoException.class)
    public static ResponseEntity<CustomErrorType> erroUmEstabelecimentoJaAtivo() {
        return new ResponseEntity<>(new CustomErrorType(ErroEstabelecimento.EXISTE_UM_ESTABELECIMENTO_ATIVO),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EstabelecimentoNotFoundException.class)
    public static ResponseEntity<CustomErrorType> erroEstabelecimentoNaoCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(ErroEstabelecimento.ESTABELECIMENTO_NAO_CADASTRADO),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstabelecimentoMustHaveAValidNameException.class)
    public static ResponseEntity<CustomErrorType> erroNomeInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroEstabelecimento.NOME_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EstabelecimentoMustHaveAValidAddressException.class)
    public static ResponseEntity<CustomErrorType> erroEnderecoInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroEstabelecimento.ENDERECO_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ThereIsNoPizzaFlavorException.class)
    public static ResponseEntity<CustomErrorType> erroSemSaboresCadastrados() {
        return new ResponseEntity<>(new CustomErrorType(ErroEstabelecimento.SEM_SABORES_DE_PIZZA), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ThereIsNoPizzaFlavorWithTypeException.class)
    public static ResponseEntity<CustomErrorType> erroSemSaboresComTipoCadastrados() {
        return new ResponseEntity<>(new CustomErrorType(ErroEstabelecimento.SEM_SABORES_DE_PIZZA_COM_TIPO), HttpStatus.NOT_FOUND);
    }
}
