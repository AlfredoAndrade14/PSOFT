package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroLote {

	static final String SEM_LOTES_CADASTRADOS = "Não há lotes cadastrados";

	static final String LOTE_NAO_CASTRADO = "Lote com id %s não está cadastrado";
	
	public static ResponseEntity<CustomErrorType> erroSemLotesCadastrados() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroLote.SEM_LOTES_CADASTRADOS),
				HttpStatus.NO_CONTENT);
	}

    public static ResponseEntity<?> erroLoteNaoEnconrtrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroLote.LOTE_NAO_CASTRADO, id)),
				HttpStatus.NOT_FOUND);
    }
}

