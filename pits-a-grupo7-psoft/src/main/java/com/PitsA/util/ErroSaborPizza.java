package com.PitsA.util;

import com.PitsA.exception.saborPizza.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroSaborPizza {

    static final String SABOR_TIPO_PIZZA_INVALIDO = "Precisa de um tipo de pizza válido.";
    static final String VALOR_PIZZA_INVALIDO = "Precisa de um valor de pizza válido.";
    static final String NOME_PIZZA_INVALIDO = "Precisa de um nome de pizza válido.";
    static final String TAMANHO_PIZZA_INVALIDO = "Precisa de um tamanho de pizza válido.";
    static final String SABOR_PIZZA_NAO_CADASTRADO = "Sabor Pizza não está cadastrado.";
    static final String SABOR_PIZZA_DISPONIVEL = "Não é possível demonstrar interesse em um sabor disponível.";
    static final String SABOR_NAO_DISPONIVEL_ESTABELECIMENTO = "O Estabelecimento não possui este sabor.";
    static final String SABOR_PIZZA_NAO_DISPONIVEL= "O sabor da pizza não está disponível";

    @ExceptionHandler(SaborPizzaMustHaveAValidTipoSaborException.class)
    public static ResponseEntity<CustomErrorType> erroSaborTipoPizzaInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.SABOR_TIPO_PIZZA_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaborPizzaMustHaveAValidValorException.class)
    public static ResponseEntity<CustomErrorType> erroValorSaborPizzaInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.VALOR_PIZZA_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaborPizzaMustHaveAValidNameException.class)
    public static ResponseEntity<CustomErrorType> erroNomeSaborPizzaInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.NOME_PIZZA_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaborPizzaMustHaveAValidTamanhoException.class)
    public static ResponseEntity<CustomErrorType> erroTamanhoSaborPizzaInvalido() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.TAMANHO_PIZZA_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaborPizzaNotFoundException.class)
    public static ResponseEntity<CustomErrorType> erroSaborPizzaNaoCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.SABOR_PIZZA_NAO_CADASTRADO),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaborPizzaIsAvailableException.class)
    public static ResponseEntity<CustomErrorType> erroSaborPizzaEstaDisponivel() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.SABOR_PIZZA_DISPONIVEL),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFlavorForStore.class)
    public static ResponseEntity<CustomErrorType> erroSaborPizzaInvalidoParaEstabelecimento() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.SABOR_NAO_DISPONIVEL_ESTABELECIMENTO),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SaborPizzaUnavailableException.class)
    public static ResponseEntity<CustomErrorType> erroSaborPizzaNaoDisponivel() {
        return new ResponseEntity<>(new CustomErrorType(ErroSaborPizza.SABOR_PIZZA_NAO_DISPONIVEL),
                HttpStatus.BAD_REQUEST);
    }
}


