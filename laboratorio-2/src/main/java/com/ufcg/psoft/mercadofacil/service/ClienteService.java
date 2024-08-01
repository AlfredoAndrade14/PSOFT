package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.exception.IllegalArgumentException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;

public interface ClienteService {

	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException;
	
	public ClienteDTO getClienteByCpf(Long cpf) throws ClienteNotFoundException;
	
	public void removerClienteCadastrado(Long id) throws ClienteNotFoundException;

	public List<ClienteDTO> listarClientes();
	
	public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException, IllegalArgumentException;
	
	public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException, IllegalArgumentException;

	public  void setCarrinho(long id, Carrinho carrinho) throws ClienteNotFoundException;

	void salvarClienteCadastrado(Cliente cliente);
}
