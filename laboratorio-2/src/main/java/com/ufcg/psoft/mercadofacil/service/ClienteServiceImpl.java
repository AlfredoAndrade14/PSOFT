package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.exception.IllegalArgumentException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	public ModelMapper modelMapper;

	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	private Cliente getClienteId(Long id) throws ClienteNotFoundException {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException());
	}

	public ClienteDTO getClienteByCpf(Long cpf) throws ClienteNotFoundException {
		Cliente cliente = clienteRepository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteNotFoundException());
		return modelMapper.map(cliente, ClienteDTO.class);
	}
		
	public void removerClienteCadastrado(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		clienteRepository.delete(cliente);
	}

	public void salvarClienteCadastrado(Cliente cliente) {
		clienteRepository.save(cliente);		
	}

	public List<ClienteDTO> listarClientes() {
		List<ClienteDTO> clientes = clienteRepository.findAll()
				.stream()
				.map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
				.collect(Collectors.toList());
		return clientes;
	}

	public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException, IllegalArgumentException {
		if(clienteDTO.getCpf() == null || clienteDTO.getNome() == null|| clienteDTO.getEndereco() == null) {
			throw new IllegalArgumentException();
		}

		if(isClienteCadastrado(clienteDTO.getCpf())) {
			throw new ClienteAlreadyCreatedException();
		}
			
		Cliente cliente = new Cliente(clienteDTO.getCpf(), clienteDTO.getNome(), 
				clienteDTO.getIdade(), clienteDTO.getEndereco());
		
		salvarClienteCadastrado(cliente);
		
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException, IllegalArgumentException {
		if(clienteDTO.getCpf() == null || clienteDTO.getNome() == null|| clienteDTO.getEndereco() == null) {
			throw new IllegalArgumentException();
		}

		Cliente cliente = getClienteId(id);
		
		cliente.setIdade(clienteDTO.getIdade());
		cliente.setEndereco(clienteDTO.getEndereco());
		cliente.setCarrinho(clienteDTO.getCarrinho());

		salvarClienteCadastrado(cliente);
		
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	@Override
	public void setCarrinho(long id, Carrinho carrinho) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		cliente.setCarrinho(carrinho);
		salvarClienteCadastrado(cliente);
	}

	private boolean isClienteCadastrado(Long cpf) {
		try {
			getClienteByCpf(cpf);
			return true;
		} catch (ClienteNotFoundException e) {
			return false;
		}
	}
}
