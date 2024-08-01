package com.PitsA.util;

import com.PitsA.exception.tipoVeiculo.TipoVeiculoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErroTipoVeiculo {

    final static String VEICULO_NAO_ENCONTRADO = "Tipo veículo não foi encontrado";

    @ExceptionHandler(TipoVeiculoNotFoundException.class)
    public ResponseEntity<CustomErrorType> erroVeiculoNaoEncontrado() {
        return new ResponseEntity<>(new CustomErrorType(ErroTipoVeiculo.VEICULO_NAO_ENCONTRADO), HttpStatus.NOT_FOUND);
    }
}
