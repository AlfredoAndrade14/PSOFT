package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;

public class ErroProduto {
	
	static final String PRODUTO_NAO_CASTRADO = "Produto com id %s não está cadastrado";
	
	static final String PRODUTOS_NAO_CASTRADOS = "Não há produtos cadastrados";

	static final String NAO_FOI_POSSIVEL_ATUALIZAR = "Não foi possível mudar atualizar a situação do produto %s "
			+ "do frabricante %s";

	static final String PRODUTO_JA_CADASTRADO = "O produto %s do fabricante %s já esta cadastrado";

	static final String PRODUTO_ESGOTADO = "O produto com id %s não está disponivel";

	static final String MENOS_PRODUTO = "O produto com id %s não possui %s unidades";

	public static ResponseEntity<CustomErrorType> erroProdutoNaoEnconrtrado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroProduto.PRODUTO_NAO_CASTRADO, id)),
				HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroSemProdutosCadastrados() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroProduto.PRODUTOS_NAO_CASTRADOS),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<?> erroMudarSituacao(Produto produto) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroProduto.NAO_FOI_POSSIVEL_ATUALIZAR,
				produto.getNome(), produto.getFabricante())), HttpStatus.NOT_MODIFIED);
	}

	public static ResponseEntity<CustomErrorType> erroProdutoEsgotado(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroProduto.PRODUTO_ESGOTADO, id)),
				HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroMenosProduto(long id, int qtd) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroProduto.MENOS_PRODUTO, id, qtd)),
				HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<?> erroProdutoJaCadastrado(ProdutoDTO produtoDTO) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroProduto.PRODUTO_JA_CADASTRADO,
				produtoDTO.getNome(), produtoDTO.getFabricante())), HttpStatus.CONFLICT);
	}
}
